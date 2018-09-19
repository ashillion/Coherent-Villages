package net.theexceptionist.coherentvillages.entity;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHarvestFarmland;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.village.Village;
import net.minecraft.world.World;
import net.theexceptionist.coherentvillages.entity.ai.EntityAIAttackBackExclude;
import net.theexceptionist.coherentvillages.entity.ai.EntityAIGuardArea;
//import net.theexceptionist.coherentvillages.entity.ai.EntityAIAttackBackExclude;
//import net.theexceptionist.coherentvillages.entity.ai.EntityAIGuardPost;
//import net.theexceptionist.coherentvillages.entity.ai.EntityAIPatrol;
import net.theexceptionist.coherentvillages.entity.ai.EntityAIGuardPost;
import net.theexceptionist.coherentvillages.entity.ai.EntityAIPatrol;
import net.theexceptionist.coherentvillages.entity.ai.EntityAISearchHouse;

public class EntityVillagerSoldier extends EntityVillager{
	private boolean hasEquip = false;
	private int attack = 7, armor = 4;
	private boolean areAdditionalTasksSet;
	public boolean wasSpawned = false;
	public BlockPos spawnPos;
	protected int homeCheckTimer; 
	protected Village village;
	protected Object buyingList;
	protected int type;
	
	public EntityVillagerSoldier(World worldIn) {
		super(worldIn);
	}
	
	public EntityVillagerSoldier(World world, int i) {
		super(world);
		this.type = i;
	}

	public void onStruckByLightning(EntityLightningBolt lightningBolt)
    {
        
    }
	
	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
		return false;
		
    }
	
	@Override
	public MerchantRecipeList getRecipes(EntityPlayer player)
    {
        if (this.buyingList == null)
        {
            //this.populateBuyingList();
        }

        return null;
    }
	
	protected void initEntityAI()
    {
	
		this.areAdditionalTasksSet = true;
		
		this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, true));
       // this.tasks.addTask(5, new EntityAIHangAroundFence(this, this.world));
        
        //this.tasks.addTask(2, new EntityAIMoveIndoors(this));
        this.tasks.addTask(2, new EntityAIRestrictOpenDoor(this));
        this.tasks.addTask(3, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(4, new EntityAIMoveTowardsTarget(this, 0.9D, 32.0F));
        this.tasks.addTask(5, new EntityAIMoveThroughVillage(this, 0.6D, true));
        this.tasks.addTask(6, new EntityAISearchHouse(this, 50));
        this.tasks.addTask(7, new EntityAIMoveTowardsRestriction(this, 1.0D));
        // this.tasks.addTask(8, new EntityAIGuardPost(this, true));
        //this.Stasks.addTask(5, new EntityAILookAtVillager(this));
        this.tasks.addTask(8, new EntityAIGuardArea(this));
        this.tasks.addTask(9, new EntityAIWanderAvoidWater(this, 0.6D));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(11, new EntityAILookIdle(this));
       // this.tasks.addTask(6, new EntityAIHarvestFarmland(this, 0.6D));
        //this.areAdditionalTasksSet = true;
        
        
        
        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget(this, EntityLiving.class, 1, false, true, new Predicate<EntityLiving>()
        {
            public boolean apply(@Nullable EntityLiving p_apply_1_)
            {
                return p_apply_1_ != null && IMob.VISIBLE_MOB_SELECTOR.apply(p_apply_1_) && !(p_apply_1_ instanceof EntityCreeper);
            }
        }));
        this.targetTasks.addTask(1, new EntityAIAttackBackExclude(this, true, new Class[0]));
        
        
    }
	
	 protected void applyEntityAttributes()
	    {
	        super.applyEntityAttributes();
        	
			type = this.world.rand.nextInt(5);
			
        	if(type == 0){
	        	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
	        	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60.0D);
	        	attack = 7;
	        	armor = 4;
	        	//spawnEquipment();
	        }else if(type == 1){
	        	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.40D);
	        	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
	        	attack = 4;
	        	armor = 0;
	        }else if(type == 2){
	        	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
	        	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
	        	attack = 5;
	        	armor = 2;
	        }else if(type == 3){
	        	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
	        	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(80.0D);
	        	attack = 15;
	        	armor = 2;
	        }else if(type == 4){
	        	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30D);
	        	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(80.0D);
	        	attack = 6;
	        	armor = 20;
	        	//spawnEquipment();
	        	//System.out.println(type);
	        }else{
	        	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30D);
	        	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60.0D);
	        	attack = 7;
	        	armor = 4;
	        }
	   }
	 
	 protected void spawnEquipment()
	 {
			if(type == 0){
				//footman
				this.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.IRON_SWORD));
				this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
				this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
				this.targetTasks.addTask(2, new EntityAIGuardPost(this, true));
		        
			}else if(type == 1){
				//Peasant
				this.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.WOODEN_SWORD));
				//this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
				//this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
			}else if(type == 2){
				//Militia
				this.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.STONE_SWORD));
				this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.CHAINMAIL_HELMET));
				this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.CHAINMAIL_CHESTPLATE));
				this.targetTasks.addTask(2, new EntityAIPatrol(this, true));
			}else if(type == 3){
				//Mercenary
				this.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.IRON_AXE));
				this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
				this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
				this.targetTasks.addTask(2, new EntityAIPatrol(this, true));
			}else if(type == 4){
				//Man at arms
				this.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.IRON_SWORD));
				this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
				this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
				this.targetTasks.addTask(2, new EntityAIGuardPost(this, true));
		        
				
			}
			
			setHasEquip(true);
	 }
	 
	 protected void damageEntity(DamageSource damageSrc, float damageAmount)
	    {
		 super.damageEntity(damageSrc, damageAmount -= armor/2);
		// System.out.println("Working");
	    }
	 
	 protected void updateAITasks()
	    {
		
		 super.updateAITasks();
		 if(this.getAttackTarget() instanceof EntityVillager){
			 this.setAttackTarget(null);
		 }
		 //System.out.println(this.getMaximumHomeDistance());
		 for(Object task : this.tasks.taskEntries.toArray())
			{
				 EntityAIBase ai = ((EntityAITaskEntry) task).action;
				 if(ai instanceof EntityAIHarvestFarmland)
					 this.tasks.removeTask(ai);	
				 //System.out.println("Removed");
			}
		 
		 if (--this.homeCheckTimer <= 0)
	        {
	            this.homeCheckTimer = 70 + this.rand.nextInt(50);
	            this.village = this.world.getVillageCollection().getNearestVillage(new BlockPos(this), 32);

	            if (this.village == null)
	            {
	                this.detachHome();
	            }
	            else
	            {
	                BlockPos blockpos = this.village.getCenter();
	                this.setHomePosAndDistance(blockpos, (int)((float)this.village.getVillageRadius() * 0.6F));
	            }
	        }
	    }
	 
	

	 public boolean attackEntityAsMob(Entity entityIn)
	    {
		 //System.out.println(this.getHeldItemMainhand().getItem().toString());
	        float f = (float)this.attack ;//+( (ItemSword)this.getHeldItemMainhand().getItem()).getDamageVsEntity();
	        int i = 0;
	        
	       // System.out.println(this+" "+attack+" Previous Damage");

	        if (entityIn instanceof EntityLivingBase)
	        {
	            f += EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((EntityLivingBase)entityIn).getCreatureAttribute());
	            i += EnchantmentHelper.getKnockbackModifier(this);
	            //this.getHeldItemMainhand().get;
	           //.out.println(this+" "+attack+" New Damage");
	        }

	        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), f);

	        if (flag)
	        {
	            if (i > 0 && entityIn instanceof EntityLivingBase)
	            {
	                ((EntityLivingBase)entityIn).knockBack(this, (float)i * 0.5F, (double)MathHelper.sin(this.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(this.rotationYaw * 0.017453292F)));
	                this.motionX *= 0.6D;
	                this.motionZ *= 0.6D;
	            }

	            int j = EnchantmentHelper.getFireAspectModifier(this);

	            if (j > 0)
	            {
	                entityIn.setFire(j * 4);
	            }

	            if (entityIn instanceof EntityPlayer)
	            {
	                EntityPlayer entityplayer = (EntityPlayer)entityIn;
	                ItemStack itemstack = this.getHeldItemMainhand();
	                ItemStack itemstack1 = entityplayer.isHandActive() ? entityplayer.getActiveItemStack() : ItemStack.EMPTY;

	                if (!itemstack.isEmpty() && !itemstack1.isEmpty() && itemstack.getItem() instanceof ItemAxe && itemstack1.getItem() == Items.SHIELD)
	                {
	                    float f1 = 0.25F + (float)EnchantmentHelper.getEfficiencyModifier(this) * 0.05F;

	                    if (this.rand.nextFloat() < f1)
	                    {
	                        entityplayer.getCooldownTracker().setCooldown(Items.SHIELD, 100);
	                        this.world.setEntityState(entityplayer, (byte)30);
	                    }
	                }
	            }

	            this.applyEnchantments(this, entityIn);
	        }

	        return flag;
	    }


	public void setSpawnPoint(double d, double k, double e) {
		this.spawnPos = new BlockPos(d, k, e);
	}


	public Village getVillage() {
		// TODO Auto-generated method stub
		return this.village;
	}
	
	@Override
	public boolean isAIDisabled()
	{
	   return false;
	}

	public boolean isShouldFollow() {
		return true;
	}

	public boolean isHasEquip() {
		return hasEquip;
	}

	public void setHasEquip(boolean hasEquip) {
		this.hasEquip = hasEquip;
	}
}
