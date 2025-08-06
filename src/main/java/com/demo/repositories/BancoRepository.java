package com.demo.repositories;

import com.demo.models.Banco;
import java.util.List;

public interface BancoRepository {
    Banco findById(Long id);

    List<Banco> findAll();

    void update(Banco banco);

}

