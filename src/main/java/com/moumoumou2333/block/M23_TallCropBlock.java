package com.moumoumou2333.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

@SuppressWarnings( "deprecation" )
public class M23_TallCropBlock extends TallPlantBlock implements Fertilizable {

    private static final int max_age = 6;
    public static final IntProperty AGE = IntProperty.of( "age", 0, max_age );
    private static final VoxelShape[] AGE_TO_SHAPE_LOWER = new VoxelShape[] {
        Block.createCuboidShape( 0.0, 0.0, 0.0, 16.0, 2.0, 16.0 ),
        Block.createCuboidShape( 0.0, 0.0, 0.0, 16.0, 4.0, 16.0 ),
        Block.createCuboidShape( 0.0, 0.0, 0.0, 16.0, 6.0, 16.0 ),
        Block.createCuboidShape( 0.0, 0.0, 0.0, 16.0, 8.0, 16.0 ),
        Block.createCuboidShape( 0.0, 0.0, 0.0, 16.0, 10.0, 16.0 ),
        Block.createCuboidShape( 0.0, 0.0, 0.0, 16.0, 10.0, 16.0 ),
        Block.createCuboidShape( 0.0, 0.0, 0.0, 16.0, 10.0, 16.0 )
    };
    private static final VoxelShape[] AGE_TO_SHAPE_UPPER = new VoxelShape[] {
        Block.createCuboidShape( 0.0, 0.0, 0.0, 16.0, 2.0, 16.0 ),
        Block.createCuboidShape( 0.0, 0.0, 0.0, 16.0, 4.0, 16.0 ),
        Block.createCuboidShape( 0.0, 0.0, 0.0, 16.0, 6.0, 16.0 ),
        Block.createCuboidShape( 0.0, 0.0, 0.0, 16.0, 8.0, 16.0 ),
        Block.createCuboidShape( 0.0, 0.0, 0.0, 16.0, 10.0, 16.0 ),
        Block.createCuboidShape( 0.0, 0.0, 0.0, 16.0, 10.0, 16.0 ),
        Block.createCuboidShape( 0.0, 0.0, 0.0, 16.0, 10.0, 16.0 )
    };

    public M23_TallCropBlock( AbstractBlock.Settings settings ) {
        super( settings );
        this.setDefaultState( ( BlockState )( ( BlockState )this.stateManager.getDefaultState() ).with( HALF, DoubleBlockHalf.LOWER ) );
    }

    @Override
    protected void appendProperties( StateManager.Builder< Block, BlockState > builder ) {
        builder.add( new Property[]{ AGE } );
        super.appendProperties( builder );
    }

    @Override
    public VoxelShape getOutlineShape( BlockState state, BlockView world, BlockPos pos, ShapeContext context ) {
        return state.get( HALF ) == DoubleBlockHalf.UPPER ? AGE_TO_SHAPE_UPPER[ state.get( AGE ) ] : AGE_TO_SHAPE_LOWER[ state.get( AGE ) ] ;
    }

    @Override
    protected boolean canPlantOnTop( BlockState floor, BlockView world, BlockPos pos ) {
        return floor.isOf( Blocks.FARMLAND );
    }

    protected IntProperty getAgeProperty() {
        return AGE;
    }

    public int getMaxAge() {
        return max_age;
    }

    public int getAge( BlockState state ) {
        return ( Integer )state.get( this.getAgeProperty() );
    }

    public BlockState withAge( int age ) {
        return ( BlockState )this.getDefaultState().with( this.getAgeProperty(), age );
    }

    public final boolean isMature( BlockState blockState ) {
        return this.getAge( blockState ) >= this.getMaxAge();
    }

    @Override
    public boolean hasRandomTicks( BlockState state ) {
        return state.get( HALF ) == DoubleBlockHalf.LOWER && !this.isMature( state );
    }

    @Override
    public void randomTick( BlockState state, ServerWorld world, BlockPos pos, Random random ) {
        if( world.getBaseLightLevel( pos, 0 ) >= 9 ) {
            float f = this.getAvailableMoisture( this, world, pos );
            if( random.nextInt( ( int )( 25.0F / f ) + 1 ) == 0 ) {
                this.tryGrow( world, pos, state, 1 );
            }
        }
    }

    protected float getAvailableMoisture( Block block, BlockView world, BlockPos pos ) {
        float f = 1.0F;
        BlockPos blockPos = pos.down();
        for( int i = -1; i <= 1; ++ i ) {
            for( int j = -1; j <= 1; ++ j ) {
                float g = 0.0F;
                BlockState blockState = world.getBlockState( blockPos.add( i, 0, j ) );
                if( blockState.isOf( Blocks.FARMLAND ) ) {
                    g = 1.0F;
                    if( ( Integer )blockState.get( FarmlandBlock.MOISTURE ) > 0 ) {
                        g = 3.0F;
                    }
                }
                if( i != 0 || j != 0 ) {
                    g /= 4.0F;
                }
                f += g;
            }
        }
        BlockPos blockPos2 = pos.north();
        BlockPos blockPos3 = pos.south();
        BlockPos blockPos4 = pos.west();
        BlockPos blockPos5 = pos.east();
        boolean bl = world.getBlockState( blockPos4 ).isOf( block ) || world.getBlockState( blockPos5 ).isOf( block );
        boolean bl2 = world.getBlockState( blockPos2 ).isOf( block ) || world.getBlockState( blockPos3 ).isOf( block );
        if( bl && bl2 ) {
            f /= 2.0F;
        } else {
            boolean bl3 = world.getBlockState( blockPos4.north() ).isOf( block ) || world.getBlockState( blockPos5.north() ).isOf( block ) || world.getBlockState( blockPos5.south() ).isOf( block ) || world.getBlockState( blockPos4.south() ).isOf( block );
            if( bl3 ) {
                f /= 2.0F;
            }
        }
        return f;
    }

    @Override
    public void onEntityCollision( BlockState state, World world, BlockPos pos, Entity entity ) {
        if ( entity instanceof RavagerEntity && world.getGameRules().getBoolean( GameRules.DO_MOB_GRIEFING ) ) {
            world.breakBlock( pos, true, entity );
        }
        super.onEntityCollision( state, world, pos, entity );
    }

    protected ItemConvertible getSeedsItem() {
        return Items.WHEAT_SEEDS;
    }

    protected ItemConvertible getFruitItem() {
        return Items.WHEAT;
    }

    @Override
    public ItemStack getPickStack( BlockView world, BlockPos pos, BlockState state ) {
        return new ItemStack( this.getSeedsItem() );
    }

    @Override
    public boolean isFertilizable( WorldView world, BlockPos pos, BlockState state, boolean isClient ) {
        return this.canGrow( state, world, pos );
    }

    @Override
    public boolean canGrow( World world, Random random, BlockPos pos, BlockState state ) {
        return this.canGrow( state, world, pos );
    }

    @Override
    public void grow( ServerWorld world, Random random, BlockPos pos, BlockState state ) {
        this.tryGrow( world, pos, state, 1 );
    }

    protected boolean canGrow( BlockState state, WorldView world, BlockPos pos ) {
        BlockPos blockPos = state.get( HALF ) == DoubleBlockHalf.LOWER ? pos : pos.down();
        BlockState blockState = world.getBlockState( blockPos );
        return !this.isMature( blockState ) && this.canPlaceAt( blockState, world, blockPos ) && this.canPlantOnTop( world.getBlockState( blockPos.down() ), world, blockPos );
    }

    protected void tryGrow( ServerWorld world, BlockPos pos, BlockState state, int amount ) {
        int i;
        BlockPos blockPos = state.get( HALF ) == DoubleBlockHalf.LOWER ? pos : pos.down();
        BlockState blockState = world.getBlockState( blockPos );
        if( this.canGrow( blockState, world, blockPos ) ) {
            i = state.get( this.getAgeProperty() ) + amount;
            world.setBlockState( blockPos, this.withAge( i ), 2 );
            world.setBlockState( blockPos.up(), this.withAge( i ).with( HALF, DoubleBlockHalf.UPPER ), 3 );
        }
    }

}