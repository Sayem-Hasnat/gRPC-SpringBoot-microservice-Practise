
package com.hasnat.grpcclientlocal.controller;

import com.hasnat.grpcclientlocal.service.GrpcClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
public class GrpcClientController {

    @Autowired
    private GrpcClientService grpcClientService;

    @GetMapping("print")
    public String printMessage(@RequestParam(defaultValue = "grPc-Client") String message) {
        return grpcClientService.sendMessage(message);
    }

}
