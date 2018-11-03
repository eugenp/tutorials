package com.baeldung.jhipster.quotes.web.rest;

import com.baeldung.jhipster.quotes.QuotesApp;

import com.baeldung.jhipster.quotes.config.SecurityBeanOverrideConfiguration;

import com.baeldung.jhipster.quotes.domain.Quote;
import com.baeldung.jhipster.quotes.repository.QuoteRepository;
import com.baeldung.jhipster.quotes.service.QuoteService;
import com.baeldung.jhipster.quotes.service.dto.QuoteDTO;
import com.baeldung.jhipster.quotes.service.mapper.QuoteMapper;
import com.baeldung.jhipster.quotes.web.rest.errors.ExceptionTranslator;
import com.baeldung.jhipster.quotes.service.dto.QuoteCriteria;
import com.baeldung.jhipster.quotes.service.QuoteQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static com.baeldung.jhipster.quotes.web.rest.TestUtil.sameInstant;
import static com.baeldung.jhipster.quotes.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the QuoteResource REST controller.
 *
 * @see QuoteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, QuotesApp.class})
public class QuoteResourceIntTest {

    private static final String DEFAULT_SYMBOL = "AAAAAAAAAA";
    private static final String UPDATED_SYMBOL = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final ZonedDateTime DEFAULT_LAST_TRADE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_TRADE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private QuoteMapper quoteMapper;
    
    @Autowired
    private QuoteService quoteService;

    @Autowired
    private QuoteQueryService quoteQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restQuoteMockMvc;

    private Quote quote;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QuoteResource quoteResource = new QuoteResource(quoteService, quoteQueryService);
        this.restQuoteMockMvc = MockMvcBuilders.standaloneSetup(quoteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Quote createEntity(EntityManager em) {
        Quote quote = new Quote()
            .symbol(DEFAULT_SYMBOL)
            .price(DEFAULT_PRICE)
            .lastTrade(DEFAULT_LAST_TRADE);
        return quote;
    }

    @Before
    public void initTest() {
        quote = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuote() throws Exception {
        int databaseSizeBeforeCreate = quoteRepository.findAll().size();

        // Create the Quote
        QuoteDTO quoteDTO = quoteMapper.toDto(quote);
        restQuoteMockMvc.perform(post("/api/quotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quoteDTO)))
            .andExpect(status().isCreated());

        // Validate the Quote in the database
        List<Quote> quoteList = quoteRepository.findAll();
        assertThat(quoteList).hasSize(databaseSizeBeforeCreate + 1);
        Quote testQuote = quoteList.get(quoteList.size() - 1);
        assertThat(testQuote.getSymbol()).isEqualTo(DEFAULT_SYMBOL);
        assertThat(testQuote.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testQuote.getLastTrade()).isEqualTo(DEFAULT_LAST_TRADE);
    }

    @Test
    @Transactional
    public void createQuoteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = quoteRepository.findAll().size();

        // Create the Quote with an existing ID
        quote.setId(1L);
        QuoteDTO quoteDTO = quoteMapper.toDto(quote);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuoteMockMvc.perform(post("/api/quotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quoteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Quote in the database
        List<Quote> quoteList = quoteRepository.findAll();
        assertThat(quoteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSymbolIsRequired() throws Exception {
        int databaseSizeBeforeTest = quoteRepository.findAll().size();
        // set the field null
        quote.setSymbol(null);

        // Create the Quote, which fails.
        QuoteDTO quoteDTO = quoteMapper.toDto(quote);

        restQuoteMockMvc.perform(post("/api/quotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quoteDTO)))
            .andExpect(status().isBadRequest());

        List<Quote> quoteList = quoteRepository.findAll();
        assertThat(quoteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = quoteRepository.findAll().size();
        // set the field null
        quote.setPrice(null);

        // Create the Quote, which fails.
        QuoteDTO quoteDTO = quoteMapper.toDto(quote);

        restQuoteMockMvc.perform(post("/api/quotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quoteDTO)))
            .andExpect(status().isBadRequest());

        List<Quote> quoteList = quoteRepository.findAll();
        assertThat(quoteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastTradeIsRequired() throws Exception {
        int databaseSizeBeforeTest = quoteRepository.findAll().size();
        // set the field null
        quote.setLastTrade(null);

        // Create the Quote, which fails.
        QuoteDTO quoteDTO = quoteMapper.toDto(quote);

        restQuoteMockMvc.perform(post("/api/quotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quoteDTO)))
            .andExpect(status().isBadRequest());

        List<Quote> quoteList = quoteRepository.findAll();
        assertThat(quoteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllQuotes() throws Exception {
        // Initialize the database
        quoteRepository.saveAndFlush(quote);

        // Get all the quoteList
        restQuoteMockMvc.perform(get("/api/quotes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quote.getId().intValue())))
            .andExpect(jsonPath("$.[*].symbol").value(hasItem(DEFAULT_SYMBOL.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].lastTrade").value(hasItem(sameInstant(DEFAULT_LAST_TRADE))));
    }
    
    @Test
    @Transactional
    public void getQuote() throws Exception {
        // Initialize the database
        quoteRepository.saveAndFlush(quote);

        // Get the quote
        restQuoteMockMvc.perform(get("/api/quotes/{id}", quote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(quote.getId().intValue()))
            .andExpect(jsonPath("$.symbol").value(DEFAULT_SYMBOL.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.lastTrade").value(sameInstant(DEFAULT_LAST_TRADE)));
    }

    @Test
    @Transactional
    public void getAllQuotesBySymbolIsEqualToSomething() throws Exception {
        // Initialize the database
        quoteRepository.saveAndFlush(quote);

        // Get all the quoteList where symbol equals to DEFAULT_SYMBOL
        defaultQuoteShouldBeFound("symbol.equals=" + DEFAULT_SYMBOL);

        // Get all the quoteList where symbol equals to UPDATED_SYMBOL
        defaultQuoteShouldNotBeFound("symbol.equals=" + UPDATED_SYMBOL);
    }

    @Test
    @Transactional
    public void getAllQuotesBySymbolIsInShouldWork() throws Exception {
        // Initialize the database
        quoteRepository.saveAndFlush(quote);

        // Get all the quoteList where symbol in DEFAULT_SYMBOL or UPDATED_SYMBOL
        defaultQuoteShouldBeFound("symbol.in=" + DEFAULT_SYMBOL + "," + UPDATED_SYMBOL);

        // Get all the quoteList where symbol equals to UPDATED_SYMBOL
        defaultQuoteShouldNotBeFound("symbol.in=" + UPDATED_SYMBOL);
    }

    @Test
    @Transactional
    public void getAllQuotesBySymbolIsNullOrNotNull() throws Exception {
        // Initialize the database
        quoteRepository.saveAndFlush(quote);

        // Get all the quoteList where symbol is not null
        defaultQuoteShouldBeFound("symbol.specified=true");

        // Get all the quoteList where symbol is null
        defaultQuoteShouldNotBeFound("symbol.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuotesByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        quoteRepository.saveAndFlush(quote);

        // Get all the quoteList where price equals to DEFAULT_PRICE
        defaultQuoteShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the quoteList where price equals to UPDATED_PRICE
        defaultQuoteShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllQuotesByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        quoteRepository.saveAndFlush(quote);

        // Get all the quoteList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultQuoteShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the quoteList where price equals to UPDATED_PRICE
        defaultQuoteShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllQuotesByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        quoteRepository.saveAndFlush(quote);

        // Get all the quoteList where price is not null
        defaultQuoteShouldBeFound("price.specified=true");

        // Get all the quoteList where price is null
        defaultQuoteShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuotesByLastTradeIsEqualToSomething() throws Exception {
        // Initialize the database
        quoteRepository.saveAndFlush(quote);

        // Get all the quoteList where lastTrade equals to DEFAULT_LAST_TRADE
        defaultQuoteShouldBeFound("lastTrade.equals=" + DEFAULT_LAST_TRADE);

        // Get all the quoteList where lastTrade equals to UPDATED_LAST_TRADE
        defaultQuoteShouldNotBeFound("lastTrade.equals=" + UPDATED_LAST_TRADE);
    }

    @Test
    @Transactional
    public void getAllQuotesByLastTradeIsInShouldWork() throws Exception {
        // Initialize the database
        quoteRepository.saveAndFlush(quote);

        // Get all the quoteList where lastTrade in DEFAULT_LAST_TRADE or UPDATED_LAST_TRADE
        defaultQuoteShouldBeFound("lastTrade.in=" + DEFAULT_LAST_TRADE + "," + UPDATED_LAST_TRADE);

        // Get all the quoteList where lastTrade equals to UPDATED_LAST_TRADE
        defaultQuoteShouldNotBeFound("lastTrade.in=" + UPDATED_LAST_TRADE);
    }

    @Test
    @Transactional
    public void getAllQuotesByLastTradeIsNullOrNotNull() throws Exception {
        // Initialize the database
        quoteRepository.saveAndFlush(quote);

        // Get all the quoteList where lastTrade is not null
        defaultQuoteShouldBeFound("lastTrade.specified=true");

        // Get all the quoteList where lastTrade is null
        defaultQuoteShouldNotBeFound("lastTrade.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuotesByLastTradeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        quoteRepository.saveAndFlush(quote);

        // Get all the quoteList where lastTrade greater than or equals to DEFAULT_LAST_TRADE
        defaultQuoteShouldBeFound("lastTrade.greaterOrEqualThan=" + DEFAULT_LAST_TRADE);

        // Get all the quoteList where lastTrade greater than or equals to UPDATED_LAST_TRADE
        defaultQuoteShouldNotBeFound("lastTrade.greaterOrEqualThan=" + UPDATED_LAST_TRADE);
    }

    @Test
    @Transactional
    public void getAllQuotesByLastTradeIsLessThanSomething() throws Exception {
        // Initialize the database
        quoteRepository.saveAndFlush(quote);

        // Get all the quoteList where lastTrade less than or equals to DEFAULT_LAST_TRADE
        defaultQuoteShouldNotBeFound("lastTrade.lessThan=" + DEFAULT_LAST_TRADE);

        // Get all the quoteList where lastTrade less than or equals to UPDATED_LAST_TRADE
        defaultQuoteShouldBeFound("lastTrade.lessThan=" + UPDATED_LAST_TRADE);
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultQuoteShouldBeFound(String filter) throws Exception {
        restQuoteMockMvc.perform(get("/api/quotes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quote.getId().intValue())))
            .andExpect(jsonPath("$.[*].symbol").value(hasItem(DEFAULT_SYMBOL.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].lastTrade").value(hasItem(sameInstant(DEFAULT_LAST_TRADE))));

        // Check, that the count call also returns 1
        restQuoteMockMvc.perform(get("/api/quotes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultQuoteShouldNotBeFound(String filter) throws Exception {
        restQuoteMockMvc.perform(get("/api/quotes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restQuoteMockMvc.perform(get("/api/quotes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingQuote() throws Exception {
        // Get the quote
        restQuoteMockMvc.perform(get("/api/quotes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuote() throws Exception {
        // Initialize the database
        quoteRepository.saveAndFlush(quote);

        int databaseSizeBeforeUpdate = quoteRepository.findAll().size();

        // Update the quote
        Quote updatedQuote = quoteRepository.findById(quote.getId()).get();
        // Disconnect from session so that the updates on updatedQuote are not directly saved in db
        em.detach(updatedQuote);
        updatedQuote
            .symbol(UPDATED_SYMBOL)
            .price(UPDATED_PRICE)
            .lastTrade(UPDATED_LAST_TRADE);
        QuoteDTO quoteDTO = quoteMapper.toDto(updatedQuote);

        restQuoteMockMvc.perform(put("/api/quotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quoteDTO)))
            .andExpect(status().isOk());

        // Validate the Quote in the database
        List<Quote> quoteList = quoteRepository.findAll();
        assertThat(quoteList).hasSize(databaseSizeBeforeUpdate);
        Quote testQuote = quoteList.get(quoteList.size() - 1);
        assertThat(testQuote.getSymbol()).isEqualTo(UPDATED_SYMBOL);
        assertThat(testQuote.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testQuote.getLastTrade()).isEqualTo(UPDATED_LAST_TRADE);
    }

    @Test
    @Transactional
    public void updateNonExistingQuote() throws Exception {
        int databaseSizeBeforeUpdate = quoteRepository.findAll().size();

        // Create the Quote
        QuoteDTO quoteDTO = quoteMapper.toDto(quote);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuoteMockMvc.perform(put("/api/quotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quoteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Quote in the database
        List<Quote> quoteList = quoteRepository.findAll();
        assertThat(quoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuote() throws Exception {
        // Initialize the database
        quoteRepository.saveAndFlush(quote);

        int databaseSizeBeforeDelete = quoteRepository.findAll().size();

        // Get the quote
        restQuoteMockMvc.perform(delete("/api/quotes/{id}", quote.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Quote> quoteList = quoteRepository.findAll();
        assertThat(quoteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Quote.class);
        Quote quote1 = new Quote();
        quote1.setId(1L);
        Quote quote2 = new Quote();
        quote2.setId(quote1.getId());
        assertThat(quote1).isEqualTo(quote2);
        quote2.setId(2L);
        assertThat(quote1).isNotEqualTo(quote2);
        quote1.setId(null);
        assertThat(quote1).isNotEqualTo(quote2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuoteDTO.class);
        QuoteDTO quoteDTO1 = new QuoteDTO();
        quoteDTO1.setId(1L);
        QuoteDTO quoteDTO2 = new QuoteDTO();
        assertThat(quoteDTO1).isNotEqualTo(quoteDTO2);
        quoteDTO2.setId(quoteDTO1.getId());
        assertThat(quoteDTO1).isEqualTo(quoteDTO2);
        quoteDTO2.setId(2L);
        assertThat(quoteDTO1).isNotEqualTo(quoteDTO2);
        quoteDTO1.setId(null);
        assertThat(quoteDTO1).isNotEqualTo(quoteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(quoteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(quoteMapper.fromId(null)).isNull();
    }
}
