package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.hibernate.annotations.Synchronize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class StockService {

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    private final StockRepository stockRepository;

    @Transactional
    public void decrease(Long id, Long quantity) {
        // get stock
        Stock stock = stockRepository.findById(id).orElseThrow();
        // 재고감소
        stock.decrease(quantity);
        // 저장
        stockRepository.save(stock);
    }// 문제점!! 레이스 컨디션 발생. -> 레이스 컨디션이란 둘 이상의 쓰레드가 공유데이터를 엑세스할 수 있고, 동시에 변경하려고 할때 발생하는 문제

//    @Transactional
    public synchronized void decreaseSync(Long id, Long quantity) {
        // get stock
        Stock stock = stockRepository.findById(id).orElseThrow();
        // 재고감소
        stock.decrease(quantity);
        // 저장
        stockRepository.save(stock);
    }// 문제점!! spring 의 @Transactional 때문에 발생 ->
    // 문제점!! 서버가 한 대일경우에는 sync를 사용할 수 있지만 서버가 두대이상일 경우 문제가 생긴다.


}
