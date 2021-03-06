package net.theexceptionist.coherentvillages.main;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSkeleton;
import net.minecraftforge.common.MinecraftForge;
import net.theexceptionist.coherentvillages.entity.EntityVillagerArrow;
import net.theexceptionist.coherentvillages.entity.EntityVillagerHorse;
import net.theexceptionist.coherentvillages.entity.EntityVillagerMerchant;
import net.theexceptionist.coherentvillages.entity.RenderVillagerHorse;
import net.theexceptionist.coherentvillages.entity.RenderVillagerKnight;
import net.theexceptionist.coherentvillages.entity.RenderVillagerMerchant;
import net.theexceptionist.coherentvillages.entity.alchemist.EntityVillagerAlchemist;
import net.theexceptionist.coherentvillages.entity.alchemist.EntityVillagerHealer;
import net.theexceptionist.coherentvillages.entity.alchemist.EntityVillagerPotionMaster;
import net.theexceptionist.coherentvillages.entity.alchemist.EntityVillagerUndeadHunter;
import net.theexceptionist.coherentvillages.entity.alchemist.RenderVillagerAlchemist;
import net.theexceptionist.coherentvillages.entity.alchemist.RenderVillagerHealer;
import net.theexceptionist.coherentvillages.entity.alchemist.RenderVillagerPotionMaster;
import net.theexceptionist.coherentvillages.entity.alchemist.RenderVillagerUndeadHunter;
import net.theexceptionist.coherentvillages.entity.archer.EntityVillagerArcher;
import net.theexceptionist.coherentvillages.entity.archer.EntityVillagerHunter;
import net.theexceptionist.coherentvillages.entity.archer.EntityVillagerMageArcher;
import net.theexceptionist.coherentvillages.entity.archer.EntityVillagerMarksman;
import net.theexceptionist.coherentvillages.entity.archer.RenderVillagerArcher;
import net.theexceptionist.coherentvillages.entity.archer.RenderVillagerArrow;
import net.theexceptionist.coherentvillages.entity.archer.RenderVillagerHunter;
import net.theexceptionist.coherentvillages.entity.archer.RenderVillagerMageArcher;
import net.theexceptionist.coherentvillages.entity.archer.RenderVillagerMarksman;
import net.theexceptionist.coherentvillages.entity.followers.EntityMerchantGuard;
import net.theexceptionist.coherentvillages.entity.followers.EntitySkeletonMinion;
import net.theexceptionist.coherentvillages.entity.followers.EntityVillagerGuardian;
import net.theexceptionist.coherentvillages.entity.followers.RenderMerchantGuard;
import net.theexceptionist.coherentvillages.entity.followers.RenderVillagerGuardian;
import net.theexceptionist.coherentvillages.entity.knight.EntityVillagerApothecary;
import net.theexceptionist.coherentvillages.entity.knight.EntityVillagerCavalier;
import net.theexceptionist.coherentvillages.entity.knight.EntityVillagerHorseArcher;
import net.theexceptionist.coherentvillages.entity.knight.EntityVillagerKnight;
import net.theexceptionist.coherentvillages.entity.knight.EntityVillagerMageKnight;
import net.theexceptionist.coherentvillages.entity.knight.EntityVillagerPaladin;
import net.theexceptionist.coherentvillages.entity.knight.RenderVillagerApothecary;
import net.theexceptionist.coherentvillages.entity.knight.RenderVillagerCavalier;
import net.theexceptionist.coherentvillages.entity.knight.RenderVillagerHorseArcher;
import net.theexceptionist.coherentvillages.entity.knight.RenderVillagerMageKnight;
import net.theexceptionist.coherentvillages.entity.knight.RenderVillagerPaladin;
import net.theexceptionist.coherentvillages.entity.mage.EntityVillagerConjurer;
import net.theexceptionist.coherentvillages.entity.mage.EntityVillagerGrandMage;
import net.theexceptionist.coherentvillages.entity.mage.EntityVillagerMage;
import net.theexceptionist.coherentvillages.entity.mage.EntityVillagerNecromancer;
import net.theexceptionist.coherentvillages.entity.mage.RenderVillagerConjurer;
import net.theexceptionist.coherentvillages.entity.mage.RenderVillagerGrandMage;
import net.theexceptionist.coherentvillages.entity.mage.RenderVillagerMage;
import net.theexceptionist.coherentvillages.entity.mage.RenderVillagerNecromancer;
import net.theexceptionist.coherentvillages.entity.soldier.EntityVillagerGuard;
import net.theexceptionist.coherentvillages.entity.soldier.EntityVillagerManAtArms;
import net.theexceptionist.coherentvillages.entity.soldier.EntityVillagerMilitia;
import net.theexceptionist.coherentvillages.entity.soldier.EntityVillagerPeasant;
import net.theexceptionist.coherentvillages.entity.soldier.EntityVillagerSergeant;
import net.theexceptionist.coherentvillages.entity.soldier.EntityVillagerWarrior;
import net.theexceptionist.coherentvillages.entity.soldier.RenderVillagerGuard;
import net.theexceptionist.coherentvillages.entity.soldier.RenderVillagerManAtArms;
import net.theexceptionist.coherentvillages.entity.soldier.RenderVillagerMilitia;
import net.theexceptionist.coherentvillages.entity.soldier.RenderVillagerPeasant;
import net.theexceptionist.coherentvillages.entity.soldier.RenderVillagerSergeant;
import net.theexceptionist.coherentvillages.entity.soldier.RenderVillagerWarrior;
import net.theexceptionist.coherentvillages.events.EventOverrideMerchantSpawn;
import net.theexceptionist.coherentvillages.events.EventOverrideVillages;
import net.theexceptionist.coherentvillages.events.PlayerConnectionEvent;

public class ClientProxy extends CommonProxy {
	public void registerRenderInformation(){
		
	}
	
	public void registerRenderers(){
		RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        
		//Soldiers
        renderManager.entityRenderMap.put(EntityVillagerGuard.class, new RenderVillagerGuard(renderManager));
        renderManager.entityRenderMap.put(EntityVillagerPeasant.class, new RenderVillagerPeasant(renderManager));
        renderManager.entityRenderMap.put(EntityVillagerSergeant.class, new RenderVillagerSergeant(renderManager));
        renderManager.entityRenderMap.put(EntityVillagerWarrior.class, new RenderVillagerWarrior(renderManager));
        renderManager.entityRenderMap.put(EntityVillagerManAtArms.class, new RenderVillagerManAtArms(renderManager));
        renderManager.entityRenderMap.put(EntityVillagerMilitia.class, new RenderVillagerMilitia(renderManager));
        
        //Archers
        renderManager.entityRenderMap.put(EntityVillagerArcher.class, new RenderVillagerArcher(renderManager));
        renderManager.entityRenderMap.put(EntityVillagerHunter.class, new RenderVillagerHunter(renderManager));
        renderManager.entityRenderMap.put(EntityVillagerMageArcher.class, new RenderVillagerMageArcher(renderManager));
        renderManager.entityRenderMap.put(EntityVillagerMarksman.class, new RenderVillagerMarksman(renderManager));
        
        //Mages
        renderManager.entityRenderMap.put(EntityVillagerMage.class, new RenderVillagerMage(renderManager));
        renderManager.entityRenderMap.put(EntityVillagerGrandMage.class, new RenderVillagerGrandMage(renderManager));
        renderManager.entityRenderMap.put(EntityVillagerConjurer.class, new RenderVillagerConjurer(renderManager));
        renderManager.entityRenderMap.put(EntityVillagerNecromancer.class, new RenderVillagerNecromancer(renderManager));
        
        //Alchemists
        renderManager.entityRenderMap.put(EntityVillagerAlchemist.class, new RenderVillagerAlchemist(renderManager));
        renderManager.entityRenderMap.put(EntityVillagerUndeadHunter.class, new RenderVillagerUndeadHunter(renderManager));
        renderManager.entityRenderMap.put(EntityVillagerHealer.class, new RenderVillagerHealer(renderManager));
        renderManager.entityRenderMap.put(EntityVillagerPotionMaster.class, new RenderVillagerPotionMaster(renderManager));
        
        //Knights
        renderManager.entityRenderMap.put(EntityVillagerKnight.class, new RenderVillagerKnight(renderManager));
        renderManager.entityRenderMap.put(EntityVillagerCavalier.class, new RenderVillagerCavalier(renderManager));
        renderManager.entityRenderMap.put(EntityVillagerMageKnight.class, new RenderVillagerMageKnight(renderManager));
        renderManager.entityRenderMap.put(EntityVillagerPaladin.class, new RenderVillagerPaladin(renderManager));
        renderManager.entityRenderMap.put(EntityVillagerHorseArcher.class, new RenderVillagerHorseArcher(renderManager));
        renderManager.entityRenderMap.put(EntityVillagerApothecary.class, new RenderVillagerApothecary(renderManager));
        

        renderManager.entityRenderMap.put(EntityVillagerMerchant.class, new RenderVillagerMerchant(renderManager));
        
        renderManager.entityRenderMap.put(EntityVillagerGuardian.class, new RenderVillagerGuardian(renderManager));
        renderManager.entityRenderMap.put(EntitySkeletonMinion.class, new RenderSkeleton(renderManager));
        renderManager.entityRenderMap.put(EntityMerchantGuard.class, new RenderMerchantGuard(renderManager));
        
        renderManager.entityRenderMap.put(EntityVillagerHorse.class, new RenderVillagerHorse(renderManager));
        //renderManager.entityRenderMap.put(EntityVillagerCreeperHunter.class, new RenderVillagerCreeperHunter(renderManager));
        //renderManager.entityRenderMap.put(EntityVillagerEvilMage.class, new RenderVillagerEvilMage(renderManager));
        
        renderManager.entityRenderMap.put(EntityVillagerArrow.class, new RenderVillagerArrow(renderManager));
}
	
	public void initEvents(){
		MinecraftForge.EVENT_BUS.register(new EventOverrideMerchantSpawn());	
		MinecraftForge.EVENT_BUS.register(new PlayerConnectionEvent());	
		MinecraftForge.TERRAIN_GEN_BUS.register(new EventOverrideVillages());
	
	}
}
