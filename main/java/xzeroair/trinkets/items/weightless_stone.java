package xzeroair.trinkets.items;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import xzeroair.trinkets.Main;
import xzeroair.trinkets.init.ModItems;
import xzeroair.trinkets.util.IsModelLoaded;

public class weightless_stone extends Item implements IBauble, IsModelLoaded {
	
	
    public weightless_stone(String name) {
    	
		setUnlocalizedName(name);
		setRegistryName(name);
		setMaxStackSize(1);
		setMaxDamage(0);
		setCreativeTab(Main.trinketstab);
		
		ModItems.ITEMS.add(this);
    }
    
  	@Override
  	public void registerModels() {
  		
  		Main.proxy.registerItemRenderer(this, 0, "inventory");
  		
  	}
  	
	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		// TODO Auto-generated method stub
		return BaubleType.TRINKET;
	}
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if(!world.isRemote) { 
			IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
			for(int i = 0; i < baubles.getSlots(); i++) 
				if((baubles.getStackInSlot(i) == null || baubles.getStackInSlot(i).isEmpty()) && baubles.isItemValidForSlot(i, player.getHeldItem(hand), player)) {
					baubles.setStackInSlot(i, player.getHeldItem(hand).copy());
					if(!player.capabilities.isCreativeMode){
						player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
					}
					onEquipped(player.getHeldItem(hand), player);
					break;
				}
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}
	
	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		if (itemstack.getItemDamage()==0 && player.ticksExisted%39==0) {
			player.addPotionEffect(new PotionEffect(MobEffects.LEVITATION,40,1,true,true));
		}
	}
	
	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int par4, boolean par5) {
		
		EntityPlayer player = (EntityPlayer) entity;
		if (entity instanceof EntityPlayer) {
			if (player.inventory.getCurrentItem() != null) {
			if (player.inventory.getCurrentItem().getItem() == ModItems.weightless_stone)
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 2, 1));
			}
		}
	}
	
	
  	@Override
  	public boolean hasEffect(ItemStack par1ItemStack) {
  		return true;
  	}
  	@Override
  	public EnumRarity getRarity(ItemStack par1ItemStack) {
  		return EnumRarity.RARE;
  	}

  	@Override
  	public String getUnlocalizedName(ItemStack par1ItemStack)
  	{
  		return super.getUnlocalizedName() + "." + par1ItemStack.getItemDamage();
  	}
  }