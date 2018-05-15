package com.poleemploi.back.persistence;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.poleemploi.back.bean.StatsObject;


@Repository
@Configuration
@Transactional
public interface StatsDAO extends CrudRepository<StatsObject,UUID>{

}
