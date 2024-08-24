package com.studies.dio.avengers.resource.avenger

import com.studies.dio.avengers.domain.avenger.Avenger
import com.studies.dio.avengers.domain.avenger.AvengerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class AvengerRepositoryImpl(
    @Autowired private val repository: AvengerEntityRepository
) : AvengerRepository {
    override fun getDetail(id: Long): Avenger? =
        repository.findByIdOrNull(id)?.toAvenger()

    override fun getAvengers(): List<Avenger>  =
        repository.findAll().map{ it.toAvenger() }

    override fun create(request: Avenger): Avenger =
        repository.save(AvengerEntity.from(request)).toAvenger()

    override fun delete(id: Long) = repository.deleteById(id)

    override fun update(avenger: Avenger): Avenger =
        repository.save(AvengerEntity.from(avenger)).toAvenger()
}
