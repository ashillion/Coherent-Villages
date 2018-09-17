package net.theexceptionist.coherentvillages;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.Logger;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.IVillageCreationHandler;
import net.theexceptionist.coherentvillages.entity.EntityMerchantGuard;
import net.theexceptionist.coherentvillages.entity.EntityVillagerAlchemist;
import net.theexceptionist.coherentvillages.entity.EntityVillagerArcher;
import net.theexceptionist.coherentvillages.entity.EntityVillagerArrow;
import net.theexceptionist.coherentvillages.entity.EntityVillagerGuardian;
import net.theexceptionist.coherentvillages.entity.EntityVillagerMage;
import net.theexceptionist.coherentvillages.entity.EntityVillagerMerchant;
import net.theexceptionist.coherentvillages.entity.EntityVillagerSoldier;
import net.theexceptionist.coherentvillages.worldgen.VillageComponentBarrackSmall;
import net.theexceptionist.coherentvillages.worldgen.VillageComponentBarracks;
import net.theexceptionist.coherentvillages.worldgen.VillageComponentBigFarm;
import net.theexceptionist.coherentvillages.worldgen.VillageComponentFence;
import net.theexceptionist.coherentvillages.worldgen.VillageComponentGuardTower;
import net.theexceptionist.coherentvillages.worldgen.VillageComponentSmallHouseWithDoor;
import net.theexceptionist.coherentvillages.worldgen.VillageComponentVillageFort;
import net.theexceptionist.coherentvillages.worldgen.VillageComponentWall;
import net.theexceptionist.coherentvillages.worldgen.VillageHandlerBarracks;
import net.theexceptionist.coherentvillages.worldgen.VillageHandlerBarracksSmall;
import net.theexceptionist.coherentvillages.worldgen.VillageHandlerBigFarm;
import net.theexceptionist.coherentvillages.worldgen.VillageHandlerFence;
import net.theexceptionist.coherentvillages.worldgen.VillageHandlerFort;
import net.theexceptionist.coherentvillages.worldgen.VillageHandlerGuardTower;
import net.theexceptionist.coherentvillages.worldgen.VillageHandlerSmallHouseWithDoor;
import net.theexceptionist.coherentvillages.worldgen.VillageHandlerWall;

@Mod(modid = Resources.MODID, name = Resources.NAME, version = Resources.VERSION)
public class Main
{
    private static Logger logger;
    
    @SidedProxy(serverSide = "net.theexceptionist.coherentvillages.CommonProxy", clientSide = "net.theexceptionist.coherentvillages.ClientProxy")
    public static CommonProxy proxy;

    @Mod.Instance(Resources.MODID)
	public static Main instance;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		System.out.println(Resources.NAME + " is loading!");
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.initEvents();
    	proxy.registerRenderers();
    	instance = this;
    	
    	List<Biome> villageBiomes = Arrays.<Biome>asList(new Biome[] {Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.COLD_BEACH, Biomes.COLD_TAIGA, Biomes.COLD_TAIGA_HILLS, Biomes.DEEP_OCEAN,
    		Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.EXTREME_HILLS_EDGE, Biomes.EXTREME_HILLS_WITH_TREES, Biomes.FOREST, Biomes.FOREST_HILLS, Biomes.FROZEN_RIVER, Biomes.FROZEN_OCEAN, Biomes.ICE_MOUNTAINS, Biomes.ICE_PLAINS,
    		Biomes.JUNGLE, Biomes.JUNGLE_EDGE, Biomes.JUNGLE_HILLS, Biomes.MESA, Biomes.MESA_CLEAR_ROCK, Biomes.MESA_ROCK, Biomes.MUSHROOM_ISLAND, Biomes.MUSHROOM_ISLAND_SHORE, Biomes.MUTATED_BIRCH_FOREST, Biomes.PLAINS, Biomes.DESERT, Biomes.SAVANNA, Biomes.TAIGA, Biomes.OCEAN,
    		Biomes.MUTATED_BIRCH_FOREST, Biomes.MUTATED_BIRCH_FOREST_HILLS, Biomes.MUTATED_DESERT, Biomes.MUTATED_EXTREME_HILLS, Biomes.MUTATED_EXTREME_HILLS_WITH_TREES, Biomes.MUTATED_FOREST, Biomes.MUTATED_ICE_FLATS, Biomes.MUTATED_JUNGLE, Biomes.MUTATED_MESA, Biomes.MUTATED_MESA, Biomes.MUTATED_MESA_CLEAR_ROCK,
    		Biomes.MUTATED_MESA_ROCK, Biomes.MUTATED_PLAINS, Biomes.MUTATED_REDWOOD_TAIGA, Biomes.MUTATED_REDWOOD_TAIGA_HILLS, Biomes.MUTATED_ROOFED_FOREST, Biomes.MUTATED_SAVANNA, Biomes.MUTATED_SAVANNA_ROCK, Biomes.MUTATED_SWAMPLAND, Biomes.MUTATED_TAIGA, Biomes.MUTATED_TAIGA_COLD, Biomes.REDWOOD_TAIGA, Biomes.REDWOOD_TAIGA_HILLS, Biomes.RIVER
    		,Biomes.RIVER, Biomes.ROOFED_FOREST, Biomes.SAVANNA_PLATEAU, Biomes.STONE_BEACH, Biomes.SWAMPLAND, Biomes.TAIGA_HILLS});
    	
    	addVillagePiece(VillageComponentBarrackSmall.class, "ViGb"); 
    	addVillageCreationHandler(new VillageHandlerBarracksSmall()); 
    	addVillagePiece(VillageComponentBarracks.class, "ViBR"); 
    	addVillageCreationHandler(new VillageHandlerBarracks()); 
    	addVillagePiece(VillageComponentFence.class, "ViFE"); 
    	addVillageCreationHandler(new VillageHandlerFence()); 
    	addVillagePiece(VillageComponentWall.class, "ViWA"); 
    	addVillageCreationHandler(new VillageHandlerWall()); 
    	addVillagePiece(VillageComponentGuardTower.class, "ViTW"); 
    	addVillageCreationHandler(new VillageHandlerGuardTower()); 
    //	addVillagePiece(VillageComponentStable.class, "ViST"); 
    	//addVillageCreationHandler(new VillageHandlerStable()); 
    	addVillagePiece(VillageComponentSmallHouseWithDoor.class, "ViSHD"); 
    	addVillageCreationHandler(new VillageHandlerSmallHouseWithDoor()); 
    	
    	/*addVillagePiece(VillageComponentWizardTower.class, "ViWW"); 
    	addVillageCreationHandler(new VillageHandlerWizardTower()); 
    	addVillagePiece(VillageComponentAlchemyHut.class, "ViAL"); 
    	addVillageCreationHandler(new VillageHandlerAlchemyHut()); */
    	addVillagePiece(VillageComponentVillageFort.class, "ViVF"); 
    	addVillageCreationHandler(new VillageHandlerFort()); 
    	addVillagePiece(VillageComponentBigFarm.class, "ViBF"); 
    	addVillageCreationHandler(new VillageHandlerBigFarm()); 
    	
    	Biome[] biomes = new Biome[villageBiomes.size()];
    	
    	for(int i = 0; i < villageBiomes.size(); i++){
    		BiomeManager.addVillageBiome(villageBiomes.get(i), true);
    		biomes[i] = villageBiomes.get(i);
    	}

    	createEntity(EntityVillagerSoldier.class, 1513, "villager_soldier", 161425, 1582224);
    	createEntity(EntityVillagerArcher.class, 1516, "villager_archer", 345895, 1985323);
    	createEntity(EntityVillagerMage.class, 1520, "villager_mage", 745895, 8985323);
    	createEntity(EntityVillagerGuardian.class, 1521, "villager_guardian", 645895, 7985323);
    	createEntity(EntityVillagerAlchemist.class, 1522, "villager_alchemist", 545895, 6985323);
    	createEntity(EntityVillagerMerchant.class, 1523, "villager_merchant", 645895, 7985323);
    	createEntity(EntityMerchantGuard.class, 1524, "villager_guard", 545895, 6985323);
    	
    	
    	EntityRegistry.addSpawn(EntityVillagerMerchant.class, 1, 1, 2, EnumCreatureType.MONSTER, biomes);//weightedProb, min, max, typeOfCreature, biomes);
    	EntityRegistry.registerModEntity(new ResourceLocation(Resources.MODID, "villager_arrow"), EntityVillagerArrow.class, "entity_villager_arrow", 1, instance,1, 1, false);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}
	
	public static void createEntity(Class entityClass, int ID, String entityName, int solidColor, int spotColor){

    	EntityRegistry.registerModEntity(new ResourceLocation(Resources.MODID+":"+entityName), entityClass, entityName, ID, instance, 128, 1, true);
    	EntityRegistry.registerEgg(new ResourceLocation(Resources.MODID+":"+entityName),  solidColor, spotColor);
    }
	
	public static void addVillagePiece(Class c, String s) 
    { 
	    try 
	    { 
	    MapGenStructureIO.registerStructureComponent(c, s);
	    } 
	    catch (Exception localException) {} 
	    } 
	
	    public static void addVillageCreationHandler(IVillageCreationHandler v) 
	    { 
	    VillagerRegistry.instance().registerVillageCreationHandler(v); 
	    //VillagerRegistry.instance().
	    
    }
}

