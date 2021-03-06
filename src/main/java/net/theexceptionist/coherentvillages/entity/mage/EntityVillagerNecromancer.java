package net.theexceptionist.coherentvillages.entity.mage;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.theexceptionist.coherentvillages.entity.EntityVillagerLighting;
import net.theexceptionist.coherentvillages.entity.followers.EntitySkeletonMinion;
import net.theexceptionist.coherentvillages.entity.followers.EntityVillagerGuardian;

public class EntityVillagerNecromancer extends AbstractVillagerMage{
	protected int coolDownNecro = 0;
	protected int critChance;
	
	public EntityVillagerNecromancer(World worldIn) {
		super(worldIn);
		this.burstCount = worldIn.rand.nextInt(5) + 5;
		this.critChance = 5;
		//this.isHostile = true;
		// TODO Auto-generated constructor stub
	}
	
	 protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
	    {
	        super.setEquipmentBasedOnDifficulty(difficulty);
	        
	        
			//this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.CHAINMAIL_HELMET));
			this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.LEATHER_CHESTPLATE));
	    }
	 
	 protected void applyEntityAttributes()
	    {
	        super.applyEntityAttributes();
	        
	        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
	        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30D);
	        getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.0D);
	        getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.0D);
	        getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(0.0D);
	        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
	    }
	 
	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
		// TODO Auto-generated method stub
currentSpawns = this.world.getEntities(EntitySkeletonMinion.class, EntitySelectors.IS_ALIVE);
EntitySkeletonMinion skeleton = null;
        boolean crit = world.rand.nextInt(100) < critChance;
		if(coolDownNecro > 0)
		{
			coolDownNecro--;
		}
		if(coolDownNecro == 0 || (crit && this.getHealth() > 5)){
				for(int i = 0; i < currentSpawns.size(); i++){
					EntitySkeletonMinion g = (EntitySkeletonMinion) currentSpawns.get(i);
				}
				// TODO Auto-generated method stub
			      this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GHAST_SCREAM, SoundCategory.NEUTRAL, 2.0F, 0.5F + this.rand.nextFloat() * 0.2F);
			       
				int amount = 1 + this.world.rand.nextInt(4);
				BlockPos spawn = new BlockPos(this.posX, this.posY, this.posZ);
				
				
				for(int i =0; i < amount; i++){
					EntitySkeletonMinion entityvillager = new EntitySkeletonMinion(this.world, this);
		          entityvillager.setLocationAndAngles(spawn.getX() + rand.nextInt(5),spawn.getY()  + rand.nextInt(5),spawn.getZ()  + rand.nextInt(5), 0.0F, 0.0F);
		          entityvillager.onInitialSpawn(this.world.getDifficultyForLocation(getPosition()), null);
		          //entityvillager.setSpawnPoint(spawn.getX()  + rand.nextInt(5),spawn.getY()  + rand.nextInt(5),spawn.getZ()  + rand.nextInt(5));
		          //entityvillager.setProfession(null); 
		          //entityvillager.finalizeMobSpawn(this.world.getDifficultyForLocation(new BlockPos(entityvillager)), (IEntityLivingData)null, false);
		          this.world.spawnEntity(entityvillager);
		          
		          if(skeleton == null) skeleton = entityvillager;
				}
				
				if(crit)
				{
					this.heal(-5);
				}
				else
				{
					coolDownNecro = 5;
				}
		}
				

		if(!this.world.isRemote){
			if(coolDown > 0)
			{
				coolDown--;
			}else
			
			if(coolDown <= 0 && skeleton != null){
				
				this.world.addWeatherEffect(new EntityVillagerLighting(this.world, skeleton.posX, skeleton.posY, skeleton.posZ, true));
				coolDown = 3;
			}
		}
		
	}

}
