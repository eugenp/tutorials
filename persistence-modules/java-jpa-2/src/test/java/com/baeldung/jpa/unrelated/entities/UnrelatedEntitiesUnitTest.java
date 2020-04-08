package com.baeldung.jpa.unrelated.entities;

import javax.persistence.*;

import com.querydsl.jpa.impl.JPAQuery;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

public class UnrelatedEntitiesUnitTest {
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    private static Cocktail mojito;
    private static Cocktail ginTonic;

    @BeforeAll
    public static void setup() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpa-h2-unrelated-entities");
        entityManager = entityManagerFactory.createEntityManager();
        mojito = new Cocktail("Mojito", 11, "Rum");
        ginTonic = new Cocktail("Gin tonic", 8.50, "Gin");
        entityManager.getTransaction().begin();
        entityManager.persist(mojito);
        entityManager.persist(ginTonic);
        entityManager.persist(new Recipe(mojito.getName(), "Some instructions"));
        entityManager.persist(new MultipleRecipe(1L, mojito.getName(), 
            "some instructions", mojito.getCategory()));
        entityManager.persist(new MultipleRecipe(2L, mojito.getName(), 
            "some other instructions", mojito.getCategory()));
        entityManager.getTransaction().commit();
    }

    @AfterAll
    public static void closeSession() {
        entityManager.close();
    }

    @Test
    public void whenQueryingForCocktailThatHasRecipe_thenTheExpectedCocktailReturned() {
        // JPA
        Cocktail cocktail = entityManager.createQuery("select c "
            + "from Cocktail c join c.recipe", Cocktail.class)
            .getSingleResult();
        verifyResult(mojito, cocktail);

        cocktail = entityManager.createQuery("select c "
            + "from Cocktail c join Recipe r "
            + "on c.name = r.cocktail", Cocktail.class)
            .getSingleResult();
        verifyResult(mojito, cocktail);

        // QueryDSL
        cocktail = new JPAQuery<Cocktail>(entityManager).from(QCocktail.cocktail)
            .join(QCocktail.cocktail.recipe)
            .fetchOne();
        verifyResult(mojito, cocktail);

        cocktail = new JPAQuery<Cocktail>(entityManager).from(QCocktail.cocktail)
            .join(QRecipe.recipe)
            .on(QCocktail.cocktail.name.eq(QRecipe.recipe.cocktail))
            .fetchOne();
        verifyResult(mojito, cocktail);
    }

    @Test
    public void whenQueryingForCocktailThatHasNotARecipe_thenTheExpectedCocktailReturned() {
        Cocktail cocktail = entityManager.createQuery("select c "
            + "from Cocktail c left join c.recipe r " 
            + "where r is null", Cocktail.class)
            .getSingleResult();
        verifyResult(ginTonic, cocktail);

        cocktail = entityManager.createQuery("select c "
            + "from Cocktail c left join Recipe r " 
            + "on c.name = r.cocktail " 
            + "where r is null", Cocktail.class)
            .getSingleResult();
        verifyResult(ginTonic, cocktail);

        QRecipe recipe = new QRecipe("alias");
        cocktail = new JPAQuery<Cocktail>(entityManager).from(QCocktail.cocktail)
            .leftJoin(QCocktail.cocktail.recipe, recipe)
            .where(recipe.isNull())
            .fetchOne();
        verifyResult(ginTonic, cocktail);

        cocktail = new JPAQuery<Cocktail>(entityManager).from(QCocktail.cocktail)
            .leftJoin(QRecipe.recipe)
            .on(QCocktail.cocktail.name.eq(QRecipe.recipe.cocktail))
            .where(QRecipe.recipe.isNull())
            .fetchOne();
        verifyResult(ginTonic, cocktail);
    }

    @Test
    public void whenQueringForCocktailThatHasRecipes_thenTheExpectedCocktailReturned() {
        // JPQL
        Cocktail cocktail = entityManager.createQuery("select c "
            + "from Cocktail c join c.recipeList", Cocktail.class)
            .getSingleResult();
        verifyResult(mojito, cocktail);

        cocktail = entityManager.createQuery("select c "
            + "from Cocktail c join MultipleRecipe mr "
            + "on mr.cocktail = c.name", Cocktail.class)
            .getSingleResult();
        verifyResult(mojito, cocktail);

        // QueryDSL
        cocktail = new JPAQuery<Cocktail>(entityManager).from(QCocktail.cocktail)
            .join(QCocktail.cocktail.recipeList)
            .fetchOne();
        verifyResult(mojito, cocktail);

        cocktail = new JPAQuery<Cocktail>(entityManager).from(QCocktail.cocktail)
            .join(QMultipleRecipe.multipleRecipe)
            .on(QCocktail.cocktail.name.eq(QMultipleRecipe.multipleRecipe.cocktail))
            .fetchOne();
        verifyResult(mojito, cocktail);
    }

    @Test
    public void whenQueryingForCocktailThatHasNotRecipes_thenTheExpectedCocktailReturned() {
        // JPQL
        Cocktail cocktail = entityManager.createQuery("select c "
            + "from Cocktail c left join c.recipeList r "
            + "where r is null", Cocktail.class)
            .getSingleResult();
        verifyResult(ginTonic, cocktail);

        cocktail = entityManager.createQuery("select c "
            + "from Cocktail c left join MultipleRecipe r " 
            + "on c.name = r.cocktail "
            + "where r is null", Cocktail.class)
            .getSingleResult();
        verifyResult(ginTonic, cocktail);

        // QueryDSL
        QMultipleRecipe multipleRecipe = new QMultipleRecipe("alias");
        cocktail = new JPAQuery<Cocktail>(entityManager).from(QCocktail.cocktail)
            .leftJoin(QCocktail.cocktail.recipeList, multipleRecipe)
            .where(multipleRecipe.isNull())
            .fetchOne();
        verifyResult(ginTonic, cocktail);

        cocktail = new JPAQuery<Cocktail>(entityManager).from(QCocktail.cocktail)
            .leftJoin(QMultipleRecipe.multipleRecipe)
            .on(QCocktail.cocktail.name.eq(QMultipleRecipe.multipleRecipe.cocktail))
            .where(QMultipleRecipe.multipleRecipe.isNull())
            .fetchOne();
        verifyResult(ginTonic, cocktail);
    }

    @Test
    public void whenQueryingForMultipleRecipes_thenTheExpectedRecipesReturned() {
        Consumer<List<MultipleRecipe>> verifyResult = recipes -> {
            assertEquals(2, recipes.size());
            recipes.forEach(r -> assertEquals(mojito.getName(), r.getCocktail()));
        };

        // JPQL
        List<MultipleRecipe> recipes = entityManager.createQuery("select distinct r "
            + "from MultipleRecipe r "
            + "join Cocktail c " 
            + "on r.baseIngredient = c.category",
        MultipleRecipe.class)
            .getResultList();
        verifyResult.accept(recipes);

        // QueryDSL
        QCocktail cocktail = QCocktail.cocktail;
        QMultipleRecipe multipleRecipe = QMultipleRecipe.multipleRecipe;
        recipes = new JPAQuery<MultipleRecipe>(entityManager).from(multipleRecipe)
            .join(cocktail)
            .on(multipleRecipe.baseIngredient.eq(cocktail.category))
            .fetch();
        verifyResult.accept(recipes);
    }

    private void verifyResult(Cocktail expectedCocktail, Cocktail queryResult) {
        assertNotNull(queryResult);
        assertEquals(expectedCocktail, queryResult);
    }
}
