package net.theexceptionist.coherentvillages.entity.soldier;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHarvestFarmland;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.Village;
import net.minecraft.village.VillageDoorInfo;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.theexceptionist.coherentvillages.entity.ai.EntityAIAttackBackExclude;
import net.theexceptionist.coherentvillages.entity.ai.EntityAIGuardPost;
import net.theexceptionist.coherentvillages.entity.ai.IVillagerGuard;

public class EntityVillagerGuard extends AbstractVillagerSoldier implements IVillagerGuard{
	public static List<VillageDoorInfo> doorsAvailiable;
	protected BlockPos post;
	protected boolean atPost;
	protected boolean changePost;
	
	public EntityVillagerGuard(World worldIn) {
		super(worldIn);
		this.atPost = false;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	 protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
	    {
	        super.setEquipmentBasedOnDifficulty(difficulty);
	        
	        //Main.logger.info("Gave Equipment");//, message, p0, p1, p2, p3, p4, p5, p6, p7);

			this.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.IRON_SWORD));
			this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
			this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
	    }
	
	protected void initEntityAI()
    {
		super.initEntityAI();

       // this.tasks.addTask(5, new EntityAIHangAroundFence(this, this.world));
        
        //this.tasks.addTask(2, new EntityAIMoveIndoors(this));

       // this.tasks.addTask(5, new EntityAIMoveThroughVillage(this, 0.6D, true));
       // this.tasks.addTask(6, new EntityAISearchHouse(this, 50));
        //this.tasks.addTask(7, new EntityAIMoveTowardsRestriction(this, 1.0D));
        // this.tasks.addTask(8, new EntityAIGuardPost(this, true));
        //this.Stasks.addTask(5, new EntityAILookAtVillager(this));
        this.tasks.addTask(1, new EntityAIGuardPost(this, world));

       // this.tasks.addTask(6, new EntityAIHarvestFarmland(this, 0.6D));
        //this.areAdditionalTasksSet = true;
        
        
   
    }
	
	
	 protected void applyEntityAttributes()
	    {
	        super.applyEntityAttributes();
	        
	        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60.0D);
	        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
	        getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.8D);
	        getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.8D);
	        getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(16.0D);
	        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
	    }
	 
	 public void onLivingUpdate()
	    {
	        super.onLivingUpdate();
	        
	        if( this.getVillage() != null)
	        {
		        if(world.isDaytime() && !changePost)
		        {
		        	changePost = true;
		        	this.post = null;
		        	doorsAvailiable  = this.getVillage().getVillageDoorInfoList();
		        } 
		        else if(!world.isDaytime() && changePost)
		        {
		        	changePost = false;
		        }
		        
		        if(this.post == null)
		        {
		        	this.post = getPostBlock();
		        }
		        else if(this.getPos() == this.post)
				{
					this.atPost = true;
				}
				else
				{
					this.atPost = false;
				}
	    	}
	    }
	 
	 protected void updateAITasks()
	    {
		
		 super.updateAITasks();

	    }
	 
	 
		private BlockPos getPostBlock() {
	    	Village village = this.getVillage();
	    	if(village != null && doorsAvailiable != null && doorsAvailiable.size() > 0){
		    	VillageDoorInfo door = doorsAvailiable.get(world.rand.nextInt(doorsAvailiable.size()));
		    	BlockPos doorPos = door.getDoorBlockPos();
		    	BlockPos possiblePosts[] = 
		    		{
		    			new BlockPos(doorPos.getX() - 1, doorPos.getY() - 1, doorPos.getZ() - 1),
		    			new BlockPos(doorPos.getX() - 1, doorPos.getY() - 1, doorPos.getZ() + 1),
		    			new BlockPos(doorPos.getX() + 1, doorPos.getY() - 1, doorPos.getZ() - 1),
		    			new BlockPos(doorPos.getX() + 1, doorPos.getY() - 1, doorPos.getZ() + 1),
		    			new BlockPos(doorPos.getX() - 1, doorPos.getY(), doorPos.getZ() - 1),
		    			new BlockPos(doorPos.getX() - 1, doorPos.getY(), doorPos.getZ() + 1),
		    			new BlockPos(doorPos.getX() + 1, doorPos.getY(), doorPos.getZ() - 1),
		    			new BlockPos(doorPos.getX() + 1, doorPos.getY(), doorPos.getZ() + 1)
		    	};
		    	BlockPos returnPost = null;
		    	
		    	for(int i = 0; i < possiblePosts.length; i++)
		    	{
		    		if(world.getBlockState(possiblePosts[i]).getBlock() == Blocks.AIR)
		        	{
		        		returnPost = possiblePosts[i];
		        	}
		    		
		    		break;
		    	}
		    	
		    	if(returnPost != null) doorsAvailiable.remove(door);
		    	
		    	return returnPost;
	    	}
	    	else
	    	{
	    		return null;
	    	}
		}
	 
	public BlockPos getPost() {
		return post;
	}

	public void setPost(BlockPos post) {
		this.post = post;
	}

	@Override
	public AbstractVillagerSoldier getSoldier() {
		return this;
	}

	@Override
	public boolean getArrived() {
		// TODO Auto-generated method stub
		return this.atPost;
	}
}
