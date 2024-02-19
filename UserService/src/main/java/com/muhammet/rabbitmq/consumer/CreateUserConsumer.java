package com.muhammet.rabbitmq.consumer;

import com.muhammet.dto.request.UserSaveRequestDto;
import com.muhammet.rabbitmq.model.CreateUserModel;
import com.muhammet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserConsumer {
    /**
     * Bu kısım RabbitMQ tarafındaki kendisine ait kuyruğu dinleyecek ve ne zaman kuyruğa bir mesaj gelse
     * bu mesajı alacak ve işlem yapacaktır.
     */
    private final UserService userService;
    @RabbitListener(queues = "auth-queue")
    public void createUserListener(CreateUserModel model){
        System.out.println("Kuyruk gelen mesaj: "+ model);
        userService.save(UserSaveRequestDto.builder()
                        .email(model.getEmail())
                        .userName(model.getUserName())
                        .authId(model.getAuthId())
                .build());
    }

}
