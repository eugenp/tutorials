package com.baeldung;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.bealdung.domain.Game;
import com.bealdung.domain.Player;
import com.bealdung.dto.GameDTO;
import java.time.Instant;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.config.Configuration;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ModelMapperTest {

  ModelMapper mapper;

  @BeforeEach
  public void setup(){
    this.mapper = new ModelMapper();
  }

  @Test
  public void whenMapGameWithExactMatch_convertsToDTO(){
    final Game game = new Game(1L, "Game 1");
    final GameDTO gameDTO = this.mapper.map(game, GameDTO.class);

    assertEquals(game.getId(), gameDTO.getId());
    assertEquals(game.getName(), gameDTO.getName());
  }

  @Test
  public void whenMapGameWithBasicPropertyMapping_convertsToDTO(){
    final Game game = new Game(1L, "Game 1");
    game.setTimestamp(Instant.now().getEpochSecond());

    final TypeMap<Game, GameDTO> propertyMapper = this.mapper.createTypeMap(Game.class, GameDTO.class);
    propertyMapper.addMapping(Game::getTimestamp, GameDTO::setCreationTime);

    final GameDTO gameDTO = this.mapper.map(game, GameDTO.class);

    assertEquals(game.getId(), gameDTO.getId());
    assertEquals(game.getName(), gameDTO.getName());
    assertEquals(game.getTimestamp(), gameDTO.getCreationTime());
  }

//  @Test
  public void whenMapGameWithDeepMapping_convertsToDTO_CollectionProxyNotWorking(){
    final Game game = new Game(1L, "Game 1");
    game.setCreator(new Player(1L, "John"));
    game.addPlayer(new Player(2L, "Bob"));

    final TypeMap<Game, GameDTO> propertyMap = this.mapper.createTypeMap(Game.class, GameDTO.class);
    propertyMap.addMappings(mapper -> mapper.map(src -> src.getPlayers().size(), GameDTO::setTotalPlayers));

    final GameDTO gameDTO = this.mapper.map(game, GameDTO.class);

    assertEquals(game.getId(), gameDTO.getId());
    assertEquals(game.getName(), gameDTO.getName());
    assertEquals(game.getPlayers().size(), gameDTO.getTotalPlayers());
  }

  @Test
  public void whenMapGameWithDeepMapping_convertsToDTO(){
    final Game game = new Game(1L, "Game 1");
    game.setCreator(new Player(1L, "John"));

    final TypeMap<Game, GameDTO> propertyMap = this.mapper.createTypeMap(Game.class, GameDTO.class);
    propertyMap.addMappings(mapper -> mapper.map(src -> src.getCreator().getName(), GameDTO::setCreator));

    final GameDTO gameDTO = this.mapper.map(game, GameDTO.class);

    assertEquals(game.getId(), gameDTO.getId());
    assertEquals(game.getName(), gameDTO.getName());
    assertEquals(game.getCreator().getName(), gameDTO.getCreator());
  }

  @Test
  public void whenMapGameWithDifferentTypedProperties_convertsToDTO(){
    final Game game = new Game(1L, "Game 1");
    game.setCreator(new Player(1L, "John"));

    final TypeMap<Game, GameDTO> propertyMap = this.mapper.createTypeMap(Game.class, GameDTO.class);
    propertyMap.addMappings(mapper -> mapper.map(src -> src.getCreator().getId(), GameDTO::setCreatorId));

    final GameDTO gameDTO = this.mapper.map(game, GameDTO.class);

    assertEquals(game.getId(), gameDTO.getId());
    assertEquals(game.getName(), gameDTO.getName());
    assertEquals("1", gameDTO.getCreatorId());
  }

  @Test
  public void whenMapGameWithSkipIdPropery_convertsToDTO(){
    final Game game = new Game(1L, "Game 1");

    final TypeMap<Game, GameDTO> propertyMap = this.mapper.createTypeMap(Game.class, GameDTO.class);
    propertyMap.addMappings(mapper -> mapper.skip(GameDTO::setId));

    final GameDTO gameDTO = this.mapper.map(game, GameDTO.class);

    assertNull(gameDTO.getId());
    assertEquals(game.getName(), gameDTO.getName());
  }

  /**
   * Can be an example of custom Converter
   */
  @Test
  public void whenMapGameWithCustomConverter_convertsToDTO(){
    final Game game = new Game(1L, "Game 1");
    game.setCreator(new Player(1L, "John"));
    game.addPlayer(new Player(2L, "Bob"));

    final TypeMap<Game, GameDTO> propertyMap = this.mapper.createTypeMap(Game.class, GameDTO.class);
    final Converter<Collection, Integer> collectionToSize = c -> c.getSource().size();
    propertyMap.addMappings(mapper -> {
      mapper.using(collectionToSize).map(Game::getPlayers, GameDTO::setTotalPlayers);
    });

    final GameDTO gameDTO = this.mapper.map(game, GameDTO.class);

    assertEquals(game.getId(), gameDTO.getId());
    assertEquals(game.getName(), gameDTO.getName());
    assertEquals(game.getPlayers().size(), gameDTO.getTotalPlayers());
  }

}
