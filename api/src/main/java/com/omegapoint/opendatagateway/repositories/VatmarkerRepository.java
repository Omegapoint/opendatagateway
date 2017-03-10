package com.omegapoint.opendatagateway.repositories;

import com.omegapoint.opendatagateway.modell.Vatmark;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface VatmarkerRepository extends ElasticsearchRepository<Vatmark, String> {

	List<Vatmark> findAllByKommun(String kommun);


}
