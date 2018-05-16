package com.poleemploi.back.persistence;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import com.poleemploi.back.bean.StatsObject;


@Transactional
@Configuration
@EnableJpaRepositories(basePackages="com.poleemploi.back.persistence", entityManagerFactoryRef="emf")
@Component
public interface StatsDAO extends CrudRepository<StatsObject,UUID>{

}
