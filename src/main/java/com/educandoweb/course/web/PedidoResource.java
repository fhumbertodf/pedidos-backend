package com.educandoweb.course.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.educandoweb.course.domain.Pedido;
import com.educandoweb.course.repository.PedidoRepository;
import com.educandoweb.course.web.errors.BadRequestAlertException;
import com.educandoweb.course.web.errors.ObjectNotFoundException;

/**
 * REST controller for managing {@link com.educandoweb.course.domain.Pedido}.
 */
@RestController
@RequestMapping("/api")
public class PedidoResource {

    private final Logger log = LoggerFactory.getLogger(PedidoResource.class);

    private static final String ENTITY_NAME = "pedido";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PedidoRepository pedidoRepository;

    public PedidoResource(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    /**
     * {@code POST  /pedidos} : Create a new pedido.
     *
     * @param pedido the pedido to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pedido, or with status {@code 400 (Bad Request)} if the pedido has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pedidos")
    public ResponseEntity<Pedido> createPedido(@RequestBody Pedido pedido) throws URISyntaxException {
        log.debug("REST request to save Pedido : {}", pedido);
        if (pedido.getId() != null) {
            throw new BadRequestAlertException(String.format("A new pedido cannot already have an ID %s idexists", ENTITY_NAME));
        }
        Pedido result = pedidoRepository.save(pedido);
        return ResponseEntity.created(new URI("/api/pedidos/" + result.getId())).body(result);
    }

    /**
     * {@code PUT  /pedidos} : Updates an existing pedido.
     *
     * @param pedido the pedido to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pedido,
     * or with status {@code 400 (Bad Request)} if the pedido is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pedido couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pedidos")
    public ResponseEntity<Pedido> updatePedido(@RequestBody Pedido pedido) throws URISyntaxException {
        log.debug("REST request to update Pedido : {}", pedido);
        if (pedido.getId() == null) {
            throw new BadRequestAlertException(String.format("Invalid id %s idnull", ENTITY_NAME));
        }
        Pedido result = pedidoRepository.save(pedido);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET  /pedidos} : get all the pedidos.
     *

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pedidos in body.
     */
    @GetMapping("/pedidos")
    public List<Pedido> getAllPedidos(@RequestParam(required = false) String filter) {
        if ("pagamento-is-null".equals(filter)) {
            log.debug("REST request to get all Pedidos where pagamento is null");
            return StreamSupport
                .stream(pedidoRepository.findAll().spliterator(), false)
                .filter(pedido -> pedido.getPagamento() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Pedidos");
        return pedidoRepository.findAll();
    }

    /**
     * {@code GET  /pedidos/:id} : get the "id" pedido.
     *
     * @param id the id of the pedido to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pedido, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pedidos/{id}")
    public ResponseEntity<Pedido> getPedido(@PathVariable Long id) {
        log.debug("REST request to get Pedido : {}", id);
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        Pedido result = pedido.orElseThrow(() -> new ObjectNotFoundException(String.format("Invalid id %s id not found", ENTITY_NAME)));
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code DELETE  /pedidos/:id} : delete the "id" pedido.
     *
     * @param id the id of the pedido to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pedidos/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        log.debug("REST request to delete Pedido : {}", id);
        pedidoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
