package com.dealer.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dealer.app.domain.Dealer;

import com.dealer.app.repository.DealerRepository;
import com.dealer.app.web.rest.util.HeaderUtil;
import com.dealer.app.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Dealer.
 */
@RestController
@RequestMapping("/api")
public class DealerResource {

    private final Logger log = LoggerFactory.getLogger(DealerResource.class);

    private static final String ENTITY_NAME = "dealer";
        
    private final DealerRepository dealerRepository;

    public DealerResource(DealerRepository dealerRepository) {
        this.dealerRepository = dealerRepository;
    }

    /**
     * POST  /dealers : Create a new dealer.
     *
     * @param dealer the dealer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dealer, or with status 400 (Bad Request) if the dealer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dealers")
    @Timed
    public ResponseEntity<Dealer> createDealer(@RequestBody Dealer dealer) throws URISyntaxException {
        log.debug("REST request to save Dealer : {}", dealer);
        if (dealer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new dealer cannot already have an ID")).body(null);
        }
        Dealer result = dealerRepository.save(dealer);
        return ResponseEntity.created(new URI("/api/dealers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dealers : Updates an existing dealer.
     *
     * @param dealer the dealer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dealer,
     * or with status 400 (Bad Request) if the dealer is not valid,
     * or with status 500 (Internal Server Error) if the dealer couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dealers")
    @Timed
    public ResponseEntity<Dealer> updateDealer(@RequestBody Dealer dealer) throws URISyntaxException {
        log.debug("REST request to update Dealer : {}", dealer);
        if (dealer.getId() == null) {
            return createDealer(dealer);
        }
        Dealer result = dealerRepository.save(dealer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dealer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dealers : get all the dealers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dealers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/dealers")
    @Timed
    public ResponseEntity<List<Dealer>> getAllDealers(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Dealers");
        Page<Dealer> page = dealerRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dealers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dealers/:id : get the "id" dealer.
     *
     * @param id the id of the dealer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dealer, or with status 404 (Not Found)
     */
    @GetMapping("/dealers/{id}")
    @Timed
    public ResponseEntity<Dealer> getDealer(@PathVariable Long id) {
        log.debug("REST request to get Dealer : {}", id);
        Dealer dealer = dealerRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dealer));
    }

    /**
     * DELETE  /dealers/:id : delete the "id" dealer.
     *
     * @param id the id of the dealer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dealers/{id}")
    @Timed
    public ResponseEntity<Void> deleteDealer(@PathVariable Long id) {
        log.debug("REST request to delete Dealer : {}", id);
        dealerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
