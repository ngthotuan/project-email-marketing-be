package com.example.projectemailmarketingbe.controller;

import com.example.projectemailmarketingbe.dto.PageResponseDto;
import com.example.projectemailmarketingbe.dto.ProxyRpDto;
import com.example.projectemailmarketingbe.dto.ProxyUpdateDto;
import com.example.projectemailmarketingbe.dto.ResponseBodyDto;
import com.example.projectemailmarketingbe.service.ProxyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/proxies")
public class ProxyController {
    private final ProxyService proxyService;

    @GetMapping("/")
    public ResponseEntity<ResponseBodyDto<List<ProxyRpDto>>> findAll() {
        ResponseBodyDto<List<ProxyRpDto>> responseBodyDto = new ResponseBodyDto<>();
        responseBodyDto.setData(proxyService.findAllProxy());
        responseBodyDto.setStatusCode(200);
        return ResponseEntity.ok(responseBodyDto);
    }

    @GetMapping("")
    public ResponseEntity<ResponseBodyDto<PageResponseDto<ProxyRpDto>>> findAll(@RequestParam(name = "page",
            required = false,
            defaultValue = "1") int page,
                                                                                @RequestParam(name = "size",
                                                                                        required = false,
                                                                                        defaultValue = "20") int size,
                                                                                @RequestParam(name = "search",
                                                                                        defaultValue = "", required =
                                                                                        false)
                                                                                        String search) {
        ResponseBodyDto<PageResponseDto<ProxyRpDto>> responseBodyDto = new ResponseBodyDto<>();
        responseBodyDto.setData(proxyService.findAllProxyWithPagingAndSearch(search, page - 1, size));
        responseBodyDto.setStatusCode(200);
        return ResponseEntity.ok(responseBodyDto);
    }

    @GetMapping("/{proxyId}")
    public ResponseEntity<ResponseBodyDto<ProxyRpDto>> findProxyById(@PathVariable("proxyId") Long proxyId) {
        ResponseBodyDto<ProxyRpDto> responseBodyDto = new ResponseBodyDto<>();
        responseBodyDto.setData(proxyService.findProxyById(proxyId));
        responseBodyDto.setStatusCode(200);
        return ResponseEntity.ok(responseBodyDto);
    }

    @DeleteMapping("/{proxyId}")
    public ResponseEntity<?> deleteProxy(@PathVariable("proxyId") Long proxyId) {
        proxyService.deleteProxyById(proxyId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/proxy")
    public ResponseEntity<?> updateProxy(@RequestBody ProxyUpdateDto proxyUpdateDto) {
        ResponseBodyDto<ProxyRpDto> responseBodyDto = new ResponseBodyDto<>();
        responseBodyDto.setData(proxyService.updateProxy(proxyUpdateDto));
        responseBodyDto.setStatusCode(200);
        return ResponseEntity.ok(responseBodyDto);
    }

    @PostMapping("/proxy")
    public ResponseEntity<?> createProxy(@RequestBody ProxyRpDto proxyRpDto){
        ResponseBodyDto<ProxyRpDto> responseBodyDto = new ResponseBodyDto<>();
        responseBodyDto.setData(proxyService.addProxy(proxyRpDto));
        responseBodyDto.setStatusCode(201);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBodyDto);
    }

    @PostMapping("")
    public ResponseEntity<?> createProxies(@RequestBody List<ProxyRpDto> proxyRpDtos) {
        ResponseBodyDto<List<ProxyRpDto>> responseBodyDto = new ResponseBodyDto<>();
        responseBodyDto.setData(proxyService.addListProxies(proxyRpDtos));
        responseBodyDto.setStatusCode(201);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBodyDto);
    }
}
