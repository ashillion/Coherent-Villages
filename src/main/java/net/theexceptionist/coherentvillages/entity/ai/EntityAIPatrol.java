package net.theexceptionist.coherentvillages.entity.ai;

import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.Village;
import net.minecraft.village.VillageDoorInfo;

public class EntityAIPatrol extends EntityAITarget {
	private boolean patrolDuty;
	private boolean set;
	private Village village;
	
	public EntityAIPatrol(EntityCreature creature, boolean checkSight) {
		super(creature, checkSight);
		patrolDuty = false;
		set = false;
		village = null;
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean shouldExecute() {
		// TODO Auto-generated method stub
		return false;
	}

	/*@Override
	public boolean shouldExecute() {
		int random = this.taskOwner.world.rand.nextInt(100);
		
		this.village = this.taskOwner.world.getVillageCollection().getNearestVillage(new BlockPos(this.taskOwner), 0);
		
        if (village == null)
        {
            return false;
        }
        else
        {
			if(this.taskOwner.world.isDaytime())
			{
				if(patrolDuty && !set)
				{
					patrolDuty = false;
				}else
				if(!patrolDuty && random < 10 && !set)
				{
					patrolDuty = true;
				}
				
				set = true;
			}
			else
			{
				if(patrolDuty && set)
				{
					patrolDuty = false;
				}
				else if(!patrolDuty && random < 10 && set)
				{
					patrolDuty = true;
				}
				
				set = false;
			}
			return patrolDuty;
        }
	}
	
	public boolean shouldContinueExecuting()
    {
        return patrolDuty && (village != null);
    }
	
    public void startExecuting()
    {
    	if(patrolDuty && village != null)
    	{
    		VillageDoorInfo door = village.getNearestDoor(new BlockPos(this.taskOwner));
    		BlockPos targetPos = door.getDoorBlockPos();
    		IBlockState targetBlock;// = door.getDoorBlockPos();
    		
    		targetPos.add(0, -1, -1);
    		
    		targetBlock = this.taskOwner.world.getBlockState(targetPos);
    		
    		if(targetBlock == Blocks.GRASS_PATH.getDefaultState() || targetBlock == Blocks.PLANKS.getDefaultState() ||
    				targetBlock == Blocks.COBBLESTONE.getDefaultState() || targetBlock == Blocks.SANDSTONE.getDefaultState()
    				|| targetBlock == Blocks.BRICK_BLOCK.getDefaultState()
    				|| targetBlock == Blocks.PLANKS.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.OAK)
    				|| targetBlock == Blocks.MOSSY_COBBLESTONE.getDefaultState()
                   	|| targetBlock == Blocks.BRICK_BLOCK.getDefaultState()
    				|| targetBlock == Blocks.GRAVEL.getDefaultState()
    				|| targetBlock == Blocks.STONEBRICK.getDefaultState() || targetBlock == Blocks.PLANKS.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.BIRCH))
    		{
    			if(this.taskOwner.world.getBlockState(new BlockPos(targetPos.getX(), targetPos.getY() + 1, targetPos.getZ())) == Blocks.AIR.getDefaultState())
    			{
    				
    			}
    			else
    			{
    				return;
    			}
    		}
    		else
    		{
    			return;
    		}
    	}
    	else
    	{
    		return;
    	}
    }
    
    public BlockPos findBlockRadius(IBlockState targetblock)
    {
    	int radius = 30;
    	double x = this.taskOwner.posX - radius;
    	double y = this.taskOwner.posY - radius;
    	double z = this.taskOwner.posY - radius;
    	
    	for(int n = 0; n < radius * 2; n++)
    	{
    		for(int o = 0; o < radius * 2; o++)
    		{
    			for(int g = 0; g < radius * 2; g++)
    			{
    				IBlockState block = this.taskOwner.world.getBlockState(new BlockPos(x, y, z));
    				
    				if(targetblock == block)// == Blocks.OAK_FENCE.getDefaultState() || block == Blocks.ACACIA_FENCE.getDefaultState() || block == Blocks.BIRCH_FENCE.getDefaultState() || block == Blocks.JUNGLE_FENCE.getDefaultState() || block == Blocks.SPRUCE_FENCE.getDefaultState() || block == Blocks.DARK_OAK_FENCE.getDefaultState())
    				{
    					return new BlockPos(x, y, z);
    				}
    				z++;
    			}
    			y++;
    		}
    		x++;
    	}
    	
    	return null;
    }*/
}
