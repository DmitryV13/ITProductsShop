package com.productshop.inventoryservice.service;

import com.productshop.inventoryservice.dto.InventoryResponse;
import com.productshop.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    
    @Transactional(readOnly = true)
    @SneakyThrows
    public List<InventoryResponse> isInStock(List<String> skuCode){
        //log.info("Tmp thread started");
        //Thread.sleep(5000);
        //log.info("Tmp thread ended");
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(e ->
                    InventoryResponse.builder()
                            .skuCode(e.getSkuCode())
                            .isInStock(e.getQuantity() > 0)
                            .build()
                ).toList();
    }
}
