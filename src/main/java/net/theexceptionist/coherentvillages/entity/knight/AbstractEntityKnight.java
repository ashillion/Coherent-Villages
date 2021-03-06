package net.theexceptionist.coherentvillages.entity.knight;

import java.util.Calendar;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.theexceptionist.coherentvillages.entity.EntityVillagerHorse;
import net.theexceptionist.coherentvillages.entity.ai.EntityAIAttackBackExclude;
import net.theexceptionist.coherentvillages.entity.ai.EntityAIStayInBorders;
import net.theexceptionist.coherentvillages.entity.followers.EntitySkeletonMinion;
import net.theexceptionist.coherentvillages.entity.soldier.AbstractVillagerSoldier;
import net.theexceptionist.coherentvillages.entity.soldier.EntityVillagerManAtArms;

public abstract class AbstractEntityKnight extends AbstractVillagerSoldier{
	protected EntityVillagerHorse horse;
	protected boolean isRiding = false;
	public static final double TROT = 0.8;
	public static final double RUNNING = 1.6;
	public static final double SPRINT = 2.4;
	//0.8 trot
	//1.6 sprint
	//2.4 Running
	
	public AbstractEntityKnight(World worldIn) {
		super(worldIn);

	}
	
	public void setRidingHorse(){
		this.horse = new EntityVillagerHorse(this.world);
        horse.setPosition((double)this.posX, (double)this.posY, (double)this.posZ);
        horse.setHorseTamed(true);
        
       /* if(world.rand.nextInt(100) < 50){
        	
        	horse.setHorseArmorStack(new ItemStack(Items.IRON_HORSE_ARMOR));
        }else if(world.rand.nextInt(100) < 20){
        	horse.setHorseArmorStack(new ItemStack(Items.GOLDEN_HORSE_ARMOR));
        	
        }else if(world.rand.nextInt(100) < 5){
        	horse.setHorseArmorStack(new ItemStack(Items.DIAMOND_HORSE_ARMOR));
        	
        	  
        this.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.IRON_SWORD));
		this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
		this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
		this.targetTasks.addTask(2, new EntityAIGuardArea(this));
        }*/
        this.world.spawnEntity(horse);
        this.startRiding(horse);
        this.isRiding = true;
	}
	

	protected void initEntityAI()
    {
		/**Need To replace these***/
        //this.tasks.addTask(0, new EntityAIAttackCharge(this, 1.0D, true));
        this.tasks.addTask(2, new EntityAIStayInBorders(this, RUNNING));
        this.tasks.addTask(5, new EntityAIMoveTowardsTarget(this, SPRINT, 32.0F));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, TROT));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
  
        if(this.isHostile) this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        
        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget(this, EntityLiving.class, 1, true, true, new Predicate<EntityLiving>()
        {
            public boolean apply(@Nullable EntityLiving p_apply_1_)
            {
            	/*if(creeperHunter)
            	{
            		return p_apply_1_ != null && (p_apply_1_ instanceof EntityCreeper);
            	}
            	else if(undeadHunter)
            	{
            		return p_apply_1_ != null && p_apply_1_.getCreatureAttribute() ==  EnumCreatureAttribute.UNDEAD && !(p_apply_1_ instanceof EntityVillagerHorse);
                }
            	else if(livingHunter)
            	{
            		return p_apply_1_ != null && (IMob.VISIBLE_MOB_SELECTOR.apply(p_apply_1_)) && !(p_apply_1_ instanceof EntityCreeper) && 
            				p_apply_1_.getCreatureAttribute() !=  EnumCreatureAttribute.UNDEAD;
            	}
            	else
            	{*/
            		return p_apply_1_ != null && (IMob.VISIBLE_MOB_SELECTOR.apply(p_apply_1_) && !(p_apply_1_ instanceof EntityCreeper) && !(p_apply_1_ instanceof EntitySkeletonMinion) && !(p_apply_1_ instanceof EntityTameable));
            	//}
            }
        }));
        this.targetTasks.addTask(1, new EntityAIAttackBackExclude(this, true, new Class[0])); 
		//super.initEntityAI();
		//this.areAdditionalTasksSet = true;
        //this.tasks.addTask(3, new EntityAIMoveThroughVillage(this, 0.6D, true));
       // this.tasks.addTask(6, new EntityAIHarvestFarmland(this, 0.6D));
    }
	
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60.0D);
        this.setProfession(1);
        
        //this.setHeldItem(EnumHand.MAIN_HAND, null);
        
//        EntityHorse horse = new EntityHorse(this.world);
//        setPosition(this.posX, this.posY, this.posZ);
//        this.world.spawnEntity(horse);
//        this.startRiding(horse);
    }
	
	
	 protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
	    {
	        super.setEquipmentBasedOnDifficulty(difficulty);
	    }
	 
	 /*public void onLivingUpdate()
	    {
		 super.onLivingUpdate();
	        if(this.horse != null)
	        {
	        	if(this.getAttackTarget() != null)
	        	{
	        		//getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
	        		this.horse.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(SPRINT);
		        	this.horse.setSprinting(true);
	        	}
	        	else
	        	{
	        		//getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
	        		this.horse.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(TROT);
		        	this.horse.setSprinting(false);
	        	}
	        	//getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
	        	//this.horse.get.setSprinting(true);
	        	//0.8 trot
	        	//1.6 sprint
	        	//2.4 Running
	        	//this.getMoveHelper().setMoveTo(this.posX + 100, this.world.getTopSolidOrLiquidBlock(new BlockPos(this.posX + 100, 80, this.posZ)).getY(), this.posZ, 2.4D);
	        	//this.setMoveForward(1);
	        	//this.horse.setMoveForward(5);
		        //this.horse.travel(this.posX + 1, this.posY, forward);
		        /*this.horse.turn(yaw, pitch);
		        this.horse.setVelocity(x, y, z);
		        this.horse.move(type, x, y, z);
		        this.horse.setMoveForward(amount);
		        this.horse.setMoveVertical(amount)
		        this.horse.setMoveStrafe(amount);
	        }
	    }*/

	 protected void updateAITasks()
	    {
		 super.updateAITasks();
		 if(this.getAttackTarget() instanceof EntityVillager){
			 this.setAttackTarget(null);
		 }
		 
		 if(!isRiding){
			 this.setRidingHorse();
		 }
		 if(this.horse == null || this.horse.isDead || this.inWater){
			 this.isRiding = false;
			 
			 EntityVillagerManAtArms entityvillager = new EntityVillagerManAtArms(this.world);
			  entityvillager.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(this.posX, this.posY, this.posZ)), null);
       	
      	entityvillager.setLocationAndAngles(this.posX + 0.5D, this.posY, this.posZ + 0.5D, 0.0F, 0.0F);
          entityvillager.setSpawnPoint(this.posX + 0.5D, this.posY, this.posZ + 0.5D);
          //entityvillager.setProfession(null);
          
          entityvillager.finalizeMobSpawn(this.world.getDifficultyForLocation(new BlockPos(entityvillager)), (IEntityLivingData)null, false);
          this.world.spawnEntity(entityvillager);
          this.setDead();
			 
		 }
	  }
	 
	   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
	    {
	        livingdata = super.onInitialSpawn(difficulty, livingdata);
	        this.setEquipmentBasedOnDifficulty(difficulty);
	        this.setEnchantmentBasedOnDifficulty(difficulty);
	        this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * difficulty.getClampedAdditionalDifficulty());
	        
	        
	   	 for(Object task : this.tasks.taskEntries.toArray())
			{
				 EntityAIBase ai = ((EntityAITaskEntry) task).action;
				 if(ai instanceof EntityAIAttackMelee)
					 this.tasks.removeTask(ai);	
				 //System.out.println("Removed");
			}
	    

/*
 * 
	        this.horse.travel(strafe, vertical, forward);
	        this.horse.turn(yaw, pitch);
	        this.horse.setVelocity(x, y, z);
	        this.horse.move(type, x, y, z);
	        this.horse.setMoveForward(amount);
	        this.horse.setMoveVertical(amount)
	        this.horse.setMoveStrafe(amount);*/
	        
	        if (this.getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty())
	        {
	            Calendar calendar = this.world.getCurrentDate();

	            /*if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F)
	            {
	                this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(this.rand.nextFloat() < 0.1F ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN));
	                this.inventoryArmorDropChances[EntityEquipmentSlot.HEAD.getIndex()] = 0.0F;
	            }*/
	        }

	        return livingdata;
	    }
	
}
