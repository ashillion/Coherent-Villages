package net.theexceptionist.coherentvillages.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.Logger;

import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.IVillageCreationHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.theexceptionist.coherentvillages.entity.EntityVillagerArrow;
import net.theexceptionist.coherentvillages.entity.EntityVillagerHorse;
import net.theexceptionist.coherentvillages.entity.EntityVillagerMerchant;
import net.theexceptionist.coherentvillages.entity.alchemist.EntityVillagerAlchemist;
import net.theexceptionist.coherentvillages.entity.alchemist.EntityVillagerHealer;
import net.theexceptionist.coherentvillages.entity.alchemist.EntityVillagerPotionMaster;
import net.theexceptionist.coherentvillages.entity.alchemist.EntityVillagerUndeadHunter;
import net.theexceptionist.coherentvillages.entity.archer.EntityVillagerArcher;
import net.theexceptionist.coherentvillages.entity.archer.EntityVillagerHunter;
import net.theexceptionist.coherentvillages.entity.archer.EntityVillagerMageArcher;
import net.theexceptionist.coherentvillages.entity.archer.EntityVillagerMarksman;
import net.theexceptionist.coherentvillages.entity.followers.EntityMerchantGuard;
import net.theexceptionist.coherentvillages.entity.followers.EntitySkeletonMinion;
import net.theexceptionist.coherentvillages.entity.followers.EntityVillagerGuardian;
import net.theexceptionist.coherentvillages.entity.knight.EntityVillagerApothecary;
import net.theexceptionist.coherentvillages.entity.knight.EntityVillagerCavalier;
import net.theexceptionist.coherentvillages.entity.knight.EntityVillagerHorseArcher;
import net.theexceptionist.coherentvillages.entity.knight.EntityVillagerKnight;
import net.theexceptionist.coherentvillages.entity.knight.EntityVillagerMageKnight;
import net.theexceptionist.coherentvillages.entity.knight.EntityVillagerPaladin;
import net.theexceptionist.coherentvillages.entity.mage.EntityVillagerConjurer;
import net.theexceptionist.coherentvillages.entity.mage.EntityVillagerGrandMage;
import net.theexceptionist.coherentvillages.entity.mage.EntityVillagerMage;
import net.theexceptionist.coherentvillages.entity.mage.EntityVillagerNecromancer;
import net.theexceptionist.coherentvillages.entity.soldier.EntityVillagerGuard;
import net.theexceptionist.coherentvillages.entity.soldier.EntityVillagerManAtArms;
import net.theexceptionist.coherentvillages.entity.soldier.EntityVillagerMilitia;
import net.theexceptionist.coherentvillages.entity.soldier.EntityVillagerPeasant;
import net.theexceptionist.coherentvillages.entity.soldier.EntityVillagerSergeant;
import net.theexceptionist.coherentvillages.entity.soldier.EntityVillagerWarrior;
import net.theexceptionist.coherentvillages.worldgen.ModMapVillageGen;
import net.theexceptionist.coherentvillages.worldgen.VillageComponentAlchemyHut;
import net.theexceptionist.coherentvillages.worldgen.VillageComponentBarrackSmall;
import net.theexceptionist.coherentvillages.worldgen.VillageComponentBarracks;
import net.theexceptionist.coherentvillages.worldgen.VillageComponentBigFarm;
import net.theexceptionist.coherentvillages.worldgen.VillageComponentFence;
import net.theexceptionist.coherentvillages.worldgen.VillageComponentGuardTower;
import net.theexceptionist.coherentvillages.worldgen.VillageComponentHunterHut;
import net.theexceptionist.coherentvillages.worldgen.VillageComponentInn;
import net.theexceptionist.coherentvillages.worldgen.VillageComponentSmallHouseWithDoor;
import net.theexceptionist.coherentvillages.worldgen.VillageComponentStable;
import net.theexceptionist.coherentvillages.worldgen.VillageComponentVillageFort;
import net.theexceptionist.coherentvillages.worldgen.VillageComponentWall;
import net.theexceptionist.coherentvillages.worldgen.VillageComponentWizardTower;
import net.theexceptionist.coherentvillages.worldgen.VillageHandlerAlchemyHut;
import net.theexceptionist.coherentvillages.worldgen.VillageHandlerBarracks;
import net.theexceptionist.coherentvillages.worldgen.VillageHandlerBarracksSmall;
import net.theexceptionist.coherentvillages.worldgen.VillageHandlerBigFarm;
import net.theexceptionist.coherentvillages.worldgen.VillageHandlerFence;
import net.theexceptionist.coherentvillages.worldgen.VillageHandlerFort;
import net.theexceptionist.coherentvillages.worldgen.VillageHandlerGuardTower;
import net.theexceptionist.coherentvillages.worldgen.VillageHandlerHunterHut;
import net.theexceptionist.coherentvillages.worldgen.VillageHandlerInn;
import net.theexceptionist.coherentvillages.worldgen.VillageHandlerSmallHouseWithDoor;
import net.theexceptionist.coherentvillages.worldgen.VillageHandlerStable;
import net.theexceptionist.coherentvillages.worldgen.VillageHandlerWall;
import net.theexceptionist.coherentvillages.worldgen.VillageHandlerWizardTower;

@Mod(modid = Resources.MODID, name = Resources.NAME, version = Resources.VERSION, updateJSON="https://github.com/TheExceptionist/Coherent-Villages/blob/master/UpdateChecker/update.json")
public class Main
{
	public static Logger logger;
    
    @SidedProxy(serverSide = "net.theexceptionist.coherentvillages.main.CommonProxy", clientSide = "net.theexceptionist.coherentvillages.main.ClientProxy")
    public static CommonProxy proxy;

    @Mod.Instance(Resources.MODID)
	public static Main instance;
    
    //"/config/"
    //private static final String config_path = "./config/coherent_config.txt";
    private static BufferedWriter writer;
    private static BufferedReader reader;
    private static File config_file;
    private static String[] config_text =
    	{
    			"#distance between villages\n",
    			"#do not set below 9!\n",
    			"max_distance=9\n",
    			"#do not set above max_distance or below 3!\n",
    			"min_distance=3\n",
    			"#Tested with values 0, 1... change default size of villages\n",
    			"size=1\n",
    			"#Spawnrate for the villagers outside the villages, 0 or -1 turns them off!\n",
    			"merchant_spawn_rate=1\n",
    			"#\n",
    			"#mark each biome name with either a 0 or 1 to turn them on/off\n",
    			"#Ex: Ocean=1 turns on the village in the ocean biome\n",
    			"#Ex: Ocean=0 turns off the village in the ocean biome\n",
    			"Ocean=1\n",
    			"Plains=1\n",
    			"Desert=1\n",
    			"Extreme Hills=1\n",
    			"Forest=1\n",
    			"Taiga=1\n",
    			"Swampland=1\n",
    			"River=1\n",
    			//"Hell=1\n",
    			//"Ocean=1\n",
    			"FrozenOcean=1\n",
    			"FrozenRiver=1\n",
    			"Ice Plains=1\n",
    			"Ice Mountains=1\n",
    			"MushroomIsland=1\n",
    			"MushroomIslandShore=1\n",
    			"Beach=1\n",
    			"DesertHills=1\n",
    			"ForestHills=1\n",
    			"TaigaHills=1\n",
    			"Extreme Hills Edge=1\n",
    			"Jungle=1\n",
    			"JungleHills=1\n",
    			"JungleEdge=1\n",
    			"DeepOcean=1\n",
    			"Stone Beach=1\n",
    			"Cold Beach=1\n",
    			"Birch Forest=1\n",
    			"Birch Forest Hills=1\n",
    			"Roofed Forest=1\n",
    			"Cold Taiga=1\n",
    			"Cold Taiga Hills=1\n",
    			"Mega Taiga=1\n",
    			"Mega Taiga Hills=1\n",
    			"Extreme Hills+=1\n",
    			"Savanna=1\n",
    			"Savanna Plateau=1\n",
    			"Mesa=1\n",
    			"Mesa Plateau F=1\n",
    			"Mesa Plateau=1\n",
    			"Sunflower Plains=1\n",
    			"Desert M=1\n",
    			"Taiga M=1\n",
    			"Swampland M=1\n",
    			"Ice Plains Spikes=1\n",
    			"Jungle M=1\n",
    			"JungleEdge M=1\n",
    			"Birch Forest M=1\n",
    			"Birch Forest Hills M=1\n",
    			"Roofed Forst M=1\n",
    			"Cold Taiga M=1\n",
    			"Mega Spruce Taiga=1\n",
    			"Redwood Taiga Hills M=1\n",
    			"Extreme Hills+ M=1\n",
    			"Savanna M=1\n",
    			"Savanna Plateau M=1\n",
    			"Mesa (Bryce)=1\n",
    			"Mesa Plateau F M=1\n",
    			"Mesa Plateau M=1\n"
    	};
    
    public static int max_distance = 9;
    public static int min_distance = 3;
    public static int merchant_spawn = 10;
    public static int village_size = 0;
    
    public static ArrayList<BiomeSpawn> biomes_spawn = new ArrayList<BiomeSpawn>();
    
    class BiomeSpawn
    {
    	public String name;
    	public boolean spawn;
    	
    	public BiomeSpawn(final String name, final int num)
    	{
    		this.name = name;
    		this.spawn = (num == 1) ? true : false;
    		//System.out.println(name+" "+spawn);
    		Main.biomes_spawn.add(this);
    	}
    }

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		System.out.println(Resources.NAME + " is loading!");
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		System.out.println("Working Directory = " +
	              System.getProperty("user.dir"));
		System.out.println(Resources.MODID+"| Checking for config file\n"+event.getModConfigurationDirectory().getAbsolutePath());//+"\n"+event.getSuggestedConfigurationFile());
		//List<String> config_text = Arrays.asList("#distance between villages", "#do not set below 9!", "max_distance=9", "#do not set above max_distance or below 3!", "min_distance=3");
		//List<String> config_output = null;
		config_file = new File(event.getSuggestedConfigurationFile().getAbsolutePath());
		
		try {
			if(config_file.createNewFile())
			{
				System.out.println(Resources.MODID+"| Config file not found! \nCreating...");	
				try {
					System.out.println("Writing to config file....");
					writer = new BufferedWriter(new FileWriter(config_file));
					
					//writer.
					for(int i = 0; i < config_text.length; i++)
					{
						writer.write(config_text[i]);
					}
					
					System.out.println("Wrote to config file!");
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} finally {
					writer.close();
				}
				
				readConfig();
			}
			else
			{
				readConfig();
			}
		} catch (IOException e) {
			//config_file.mkdirs();
			e.printStackTrace();
		}
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
	}

	private void readConfig() throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		System.out.println(Resources.MODID+"| Config file found! \nLoading...");
		
		try {
			String line;
			
			System.out.print("Reading from config file.....");
			reader = new BufferedReader(new FileReader(config_file));
			
			while((line = reader.readLine()) != null)
			{
				if(line.substring(0, 1).compareTo("#") == 0) continue;
				
				String[] parts = line.split("=");
				
				if(parts[0].contains("max"))
				{
					max_distance = Integer.parseInt(parts[1]);
				}
				else if(parts[0].contains("min"))
				{
					min_distance = Integer.parseInt(parts[1]);
				}
				else if(parts[0].contains("mer"))
				{
					merchant_spawn = Integer.parseInt(parts[1]);
				}
				else if(parts[0].contains("size"))
				{
					village_size = Integer.parseInt(parts[1]);
				}
				else
				{
					//System.out.println(parts[0]+" "+parts[1]);
					new BiomeSpawn(parts[0], Integer.parseInt(parts[1]));
				}
			}
			
			
			System.out.println("Read the config file! New Max: "+max_distance+" New Min: "+min_distance+" New Merchant: "+merchant_spawn);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.initEvents();
    	proxy.registerRenderers();
    	instance = this;
    	
    	List<Biome> villageBiomes = Arrays.<Biome>asList(new Biome[] {Biomes.PLAINS, Biomes.DESERT, Biomes.SAVANNA, Biomes.TAIGA, Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.COLD_BEACH, Biomes.COLD_TAIGA, Biomes.COLD_TAIGA_HILLS, Biomes.DEEP_OCEAN,
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
    	addVillagePiece(VillageComponentStable.class, "ViST"); 
    	addVillageCreationHandler(new VillageHandlerStable()); 
    	addVillagePiece(VillageComponentSmallHouseWithDoor.class, "ViSHD"); 
    	addVillageCreationHandler(new VillageHandlerSmallHouseWithDoor()); 
    	
    	addVillagePiece(VillageComponentWizardTower.class, "ViWW"); 
    	addVillageCreationHandler(new VillageHandlerWizardTower()); 
    	addVillagePiece(VillageComponentInn.class, "ViIN"); 
    	addVillageCreationHandler(new VillageHandlerInn()); 
    	addVillagePiece(VillageComponentAlchemyHut.class, "ViAL"); 
    	addVillageCreationHandler(new VillageHandlerAlchemyHut()); 
    	addVillagePiece(VillageComponentVillageFort.class, "ViVF"); 
    	addVillageCreationHandler(new VillageHandlerFort()); 
    	addVillagePiece(VillageComponentBigFarm.class, "ViBF"); 
    	addVillageCreationHandler(new VillageHandlerBigFarm());
    	addVillagePiece(VillageComponentHunterHut.class, "ViHH"); 
    	addVillageCreationHandler(new VillageHandlerHunterHut()); 
 
    	

    	int i = 0;
    	for(Biome b : villageBiomes)
    	{
    		for(BiomeSpawn s : biomes_spawn)
    		{
    			if(FMLCommonHandler.instance().getSide() == Side.CLIENT){
	    			if(b.getBiomeName().compareTo(s.name) == 0 && s.spawn)
	    			{
	    				ModMapVillageGen.VILLAGE_SPAWN_BIOMES.add(b);
	    	    		/*if(b != Biomes.MUSHROOM_ISLAND || b != Biomes.MUSHROOM_ISLAND_SHORE)
	    	    		{
	        	    		
	    	    			biomes[i] = b;
	    	    			//System.out.println(biomes[i]);
	    	    		}*/
	    	    		break;
	    			}
	    			else if(b.getBiomeName().compareTo(s.name) == 0 && !s.spawn)
	    			{
	    				ModMapVillageGen.VILLAGE_SPAWN_BIOMES.remove(b);
	    				System.out.println("Preventing: "+s.name+" from spawning.");
	    				break;
	    			}
    			} 
    			else
    			{
    				//Must ignore config
    				BiomeManager.addVillageBiome(b, true);
    			}
    		}

    		

    		i++;
    	}
    	
    	Biome[] biomes = new Biome[villageBiomes.size()];
    	
    	//System.exit(0);
    	//Soldiers
    	createEntity(EntityVillagerGuard.class, 1513, "villager_guard", 161425, 1582224);
    	createEntity(EntityVillagerManAtArms.class, 1514, "villager_man_at_arms", 261425, 1582224);
    	createEntity(EntityVillagerSergeant.class, 1515, "villager_sergeant", 361425, 1582224);
    	createEntity(EntityVillagerWarrior.class, 1516, "villager_warrior", 461425, 1382224);
    	createEntity(EntityVillagerPeasant.class, 1517, "villager_peasant", 561425, 1582224);
    	createEntity(EntityVillagerMilitia.class, 1518, "villager_militia", 661425, 1582224);
    	
    	
    	createEntity(EntityVillagerArcher.class, 1520, "villager_archer", 345895, 1985323);
    	createEntity(EntityVillagerHunter.class, 1521, "villager_hunter", 225395, 2015567);
    	createEntity(EntityVillagerMageArcher.class, 1522, "villager_mage_archer", 345895, 3985323);
    	createEntity(EntityVillagerMarksman.class, 1523, "villager_marksman", 225395, 4015567);
    	

    	createEntity(EntityVillagerMage.class, 1524, "villager_mage", 705895, 8985323);
    	createEntity(EntityVillagerGrandMage.class, 1525, "villager_grand_mage", 416395, 8115567);
    	createEntity(EntityVillagerConjurer.class, 1526, "villager_conjurer", 426395, 8215567);
    	createEntity(EntityVillagerNecromancer.class, 1527, "villager_necromancer", 436395, 8315567);
    	
    	createEntity(EntityVillagerAlchemist.class, 1528, "villager_alchemist", 505895, 3985323);
    	createEntity(EntityVillagerUndeadHunter.class, 1529, "villager_undeadhunter", 515895, 6485323);
    	createEntity(EntityVillagerHealer.class, 1540, "villager_healer", 245895, 6585323);
    	createEntity(EntityVillagerPotionMaster.class, 1541, "villager_potion_master", 545895, 2585323);
    	
    	createEntity(EntityVillagerKnight.class, 1545, "villager_knight", 385895, 3685111);
    	createEntity(EntityVillagerMageKnight.class, 1546, "villager_mage_knight", 385895, 0000111);
    	createEntity(EntityVillagerPaladin.class, 1547, "villager_paladin", 385895, 2222111);
    	createEntity(EntityVillagerHorseArcher.class, 1548, "villager_horse_archer", 385895, 4444111);
    	createEntity(EntityVillagerApothecary.class, 1549, "villager_apothecary", 385895, 5555111);
    	createEntity(EntityVillagerCavalier.class, 1550, "villager_cavalier", 385895, 3685111);
    	
    	createEntity(EntityVillagerGuardian.class, 1551, "villager_guardian", 615895, 7285323);
    	createEntity(EntityVillagerMerchant.class, 1552, "villager_merchant", 805890, 9105323);
    	createEntity(EntityMerchantGuard.class, 1553, "villager_merchant_guard", 525895, 4785000);
    	createEntity(EntityVillagerHorse.class, 1554, "villager_horse", 345895, 5505567);

    	createEntity(EntitySkeletonMinion.class, 1555, "skeleton_minion", 926395, 1015567);

    	//if(biomes != null)
    	//{
    		//System.out.println(biomes[0]);
    	/*if(merchant_spawn > 0){
    		EntityRegistry.addSpawn(EntityVillagerMerchant.class, 1, 1, 2, EnumCreatureType.MONSTER, villageBiomes.toArray(biomes));//weightedProb, min, max, typeOfCreature, biomes);
    	}*/
    	//}
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


