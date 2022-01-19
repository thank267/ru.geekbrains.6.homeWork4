package com.geekbrains.spring.web.endpoints;

import com.geekbrains.spring.web.services.ProductsService;
import com.geekbrains.spring.web.soap.products.GetAllProductsRequest;
import com.geekbrains.spring.web.soap.products.GetAllProductsResponse;
import com.geekbrains.spring.web.soap.products.GetProductByIdRequest;
import com.geekbrains.spring.web.soap.products.GetProductByIdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@RequiredArgsConstructor
public class ProductEndpoint {
    private static final String NAMESPACE_URI = "http://geekbrains.com/spring/web/soap/products";
    private final ProductsService productService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllProductsRequest")
    @ResponsePayload
    public GetAllProductsResponse getAllProducts(@RequestPayload GetAllProductsRequest request) {
        GetAllProductsResponse response = new GetAllProductsResponse();
        productService.findAll().forEach(response.getProducts()::add);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetProductByIdRequest")
    @ResponsePayload
    @Transactional
    public GetProductByIdResponse getProductsById(@RequestPayload GetProductByIdRequest request) {
        GetProductByIdResponse response = new GetProductByIdResponse();
        response.setProduct(productService.findByIdSoap(request.getId()).orElseThrow());
        return response;
    }
}
