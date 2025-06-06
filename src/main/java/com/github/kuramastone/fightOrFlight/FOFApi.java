package com.github.kuramastone.fightOrFlight;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.github.kuramastone.fightOrFlight.entity.WrappedPokemon;
import com.github.kuramastone.fightOrFlight.utils.ConfigOptions;
import com.github.kuramastone.fightOrFlight.utils.PokeUtils;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class FOFApi {

    private Map<PokemonEntity, WrappedPokemon> wrappedPokemonEntityList;

    private AttackManager attackManager;
    private WandManager wandManager;

    private ConfigOptions configOptions;

    public void init() {
        wrappedPokemonEntityList = new HashMap<>();
        attackManager = new AttackManager();
        wandManager = new WandManager();

        configOptions = new ConfigOptions();
    }

    public @Nullable WrappedPokemon getWrappedPokemon(PokemonEntity entity) {
        return wrappedPokemonEntityList.get(entity);
    }

    public void removeWrappedPokemon(PokemonEntity entity) {
        wrappedPokemonEntityList.remove(entity);
    }

    public AttackManager getAttackManager() {
        return attackManager;
    }

    public WandManager getWandManager() {
        return wandManager;
    }

    public ConfigOptions getConfigOptions() {
        return configOptions;
    }

    public void newWrappedPokemon(PokemonEntity pokemonEntity) {
        this.wrappedPokemonEntityList.put(pokemonEntity, new WrappedPokemon(pokemonEntity));
    }

    /**
     * @return True if this pokemon cannot be targetted
     */
    public boolean isPokemonProtected(PokemonEntity pokemonEntity) {
        return pokemonEntity.getOwnerUUID() == null
                && PokeUtils.doAnyAspectsMatch(configOptions.aggressionDisabledAspects, pokemonEntity);
    }

    public boolean isDisabledInWorld(Level level) {
        String world_name = level.dimension().location().toString();
        return configOptions.disabledWorlds.contains(world_name);
    }

    /**
     * Checks whether the PokeWand is prevented from working on other players
     *
     * @return True if targeting players with the PokeWand is disabled
     */
    public boolean isPvpDisabled() {
        return configOptions.disableWandOnPlayers;
    }

}
