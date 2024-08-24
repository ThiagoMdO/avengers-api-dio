package com.studies.dio.avengers.application.web.resource

import com.studies.dio.avengers.application.web.resource.request.AvengerRequest
import com.studies.dio.avengers.application.web.resource.response.AvengerResponse
import com.studies.dio.avengers.domain.avenger.AvengerRepository
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

private const val API_PATH = "/v1/api/avengers";

@RestController
@RequestMapping(API_PATH)
class AvengerResource(
    @Autowired private val repository: AvengerRepository
) {

//    fun getAvengers(): ResponseEntity<List<AvengerResponse>> =
//        ResponseEntity.ok().body(emptyList<AvengerResponse>())

//    @GetMapping
//    fun getAvengers() =  ResponseEntity.ok().body(emptyList<AvengerResponse>())

    @GetMapping
    fun getAvengers() =  repository.getAvengers()
        .map { AvengerResponse.from(it) }
        .let { ResponseEntity.ok().body(it) }

//    @GetMapping("/{id}")
//    fun getAvengerDetails(@PathParam("id") id: Long) = ResponseEntity.ok().build<AvengerResponse>()

    @GetMapping("/{id}")
    fun getAvengerDetails(@PathVariable("id") id: Long) =
        repository.getDetail(id)?.let {
                ResponseEntity.ok().body(AvengerResponse.from(it))
            } ?: ResponseEntity.notFound().build<Void>()


//    @PostMapping
//    fun createAvenger(@Valid @RequestBody request:AvengerRequest) = ResponseEntity.status(HttpStatus.CREATED).build<AvengerResponse>()

    @PostMapping
    fun createAvenger(@Valid @RequestBody request:AvengerRequest) =
        request.toAvenger().run {
            repository.create(this)
        }.let {
           ResponseEntity
               .created(URI("$API_PATH/${it.id}"))
               .body(AvengerResponse.from(it))
        }

    @PutMapping("/{id}/detail")
    fun updateAvenger(@PathVariable("id") id:Long, @Valid @RequestBody request: AvengerRequest) =
        repository.getDetail(id)?.let {
            AvengerRequest.to(it.id, request).apply {
                repository.update(this)
            }.let {
                avenger ->
                ResponseEntity.accepted().body(AvengerResponse.from(avenger))
            }
        } ?: ResponseEntity.notFound().build<Void>()

    @DeleteMapping("/{id}")
    fun deleteAvenger(@PathVariable id: Long) =
        repository.getDetail(id)?.let {
            repository.delete(id).let {
                ResponseEntity.noContent().build<Void>()
            }
        }

}
