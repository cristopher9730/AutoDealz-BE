package com.autos.service;

import com.autos.AppProperties;
import com.autos.domain.Car;
import com.autos.repository.CarRepository;
import com.azure.spring.cloud.core.service.AzureServiceType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AppProperties appProperties;

    public void processCar(MultipartFile file, String carString) throws IOException {
        try{
            Car car = objectMapper.readValue(carString,Car.class);
            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            File tempFile =  new File(String.format("%s\\%s-%s",appProperties.getTempFolder(),System.currentTimeMillis(),file.getOriginalFilename()));
            Files.write(tempFile.toPath(),file.getBytes());
            entityBuilder.addBinaryBody("file",tempFile,ContentType.DEFAULT_BINARY,LocalDateTime.now().toString());
            entityBuilder.addPart("upload_preset",new StringBody(appProperties.getUploadPreset()));
            HttpClient client = new DefaultHttpClient();
            HttpPost request = new HttpPost(appProperties.getCloudinaryUploadUrl());
            request.setEntity(entityBuilder.build());
            HttpResponse response = client.execute(request);
            tempFile.delete();
        }catch (Exception e){
            throw e;
        }
    }

    public void upsert(Car car){
        if(car.getId() == null){
            car.setId(String.format("%s-%s-%s",car.getBrand(),car.getModel(), LocalDateTime.now()));
            carRepository.save(car);
            return;
        }
        carRepository.update(car);
    }
    public List<Car> get(){
        return carRepository.getAllCars();
    }

    public void delete(String carId){
        carRepository.delete(carId);
    }
}
