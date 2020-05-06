package mod.elite_puddle.minecraftedu.objects.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;


public class Not_Block extends Block {
	public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	
	public Not_Block(Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState()
		.with(FACING, Direction.NORTH).with(LIT, Boolean.valueOf(true)));
	}
	
	//sets the block to always be placed facing the player
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState()
		.with(FACING, context.getPlacementHorizontalFacing().getOpposite())
		.with(LIT, Boolean.valueOf(context.getWorld().isBlockPowered(context.getPos())));
	}
		
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(FACING);
		builder.add(LIT);
	}
	public int tickRate(IWorldReader worldIn) {
	      return 2;
	   }

	   public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
	      for(Direction direction : Direction.values()) {
	    	  if(direction == Direction.NORTH) {
	    		  worldIn.notifyNeighborsOfStateChange(pos.offset(direction), this);
	    	  }
	      }
	   }

	   public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
	      if (!isMoving) {
	         for(Direction direction : Direction.values()) {
	        	 if(direction == Direction.NORTH) {
		    		  worldIn.notifyNeighborsOfStateChange(pos.offset(direction), this);
		    	  }
	         }
	      }
	   }

	   public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
	      return blockState.get(LIT) && Direction.NORTH != side ? 15 : 0;
	   }

	   protected boolean shouldBeOff(World worldIn, BlockPos pos, BlockState state) {
	      return worldIn.isSidePowered(pos.func_177977_b(), Direction.SOUTH);
	   }

	   public void func_225534_a_(BlockState state, ServerWorld worldIn, BlockPos pos, Random p_225534_4_) {
	      update(state, worldIn, pos, this.shouldBeOff(worldIn, pos, state));
	   }

	   public static void update(BlockState state, World worldIn, BlockPos pos, boolean isLit) {
	      if (state.get(LIT)) {
	         if (isLit) {
	            worldIn.setBlockState(pos, state.with(LIT, Boolean.valueOf(false)), 3);
	         }
	      } else if (!isLit) {
	         worldIn.setBlockState(pos, state.with(LIT, Boolean.valueOf(true)), 3);
	      }
	   }

	   public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
	      if (state.get(LIT) == this.shouldBeOff(worldIn, pos, state) && !worldIn.getPendingBlockTicks().isTickPending(pos, this)) {
	         worldIn.getPendingBlockTicks().scheduleTick(pos, this, this.tickRate(worldIn));
	      }

	   }

	   public int getStrongPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
	      return side == Direction.NORTH ? blockState.getWeakPower(blockAccess, pos, Direction.NORTH) : 0;
	   }

	   public boolean canProvidePower(BlockState state) {
	      return true;
	   }

	   public static class Toggle {
		      private final BlockPos pos;
		      private final long time;

		      public Toggle(BlockPos pos, long time) {
		         this.pos = pos;
		         this.time = time;
		      }
		   }

		
}