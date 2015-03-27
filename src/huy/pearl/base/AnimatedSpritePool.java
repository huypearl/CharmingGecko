package huy.pearl.base;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.pool.GenericPool;

public class AnimatedSpritePool extends GenericPool<AnimatedSprite> {
	ITiledTextureRegion mITiledTextureRegion;
	VertexBufferObjectManager mVertexBufferObjectManager;

	public AnimatedSpritePool(ITiledTextureRegion mITiledTextureRegion,
			VertexBufferObjectManager mVertexBufferObjectManager) {
		this.mITiledTextureRegion = mITiledTextureRegion;
		this.mVertexBufferObjectManager = mVertexBufferObjectManager;
	}

	@Override
	protected AnimatedSprite onAllocatePoolItem() {
		return new AnimatedSprite(0, 0, this.mITiledTextureRegion,
				this.mVertexBufferObjectManager);
	}

	public synchronized AnimatedSprite obtainPoolItem(final float pX,
			final float pY) {
		AnimatedSprite animatedSprite = super.obtainPoolItem();
		animatedSprite.setPosition(pX, pY);
		animatedSprite.setVisible(true);
		animatedSprite.setIgnoreUpdate(false);
		return animatedSprite;
	}

	@Override
	protected void onHandleRecycleItem(AnimatedSprite pItem) {
		super.onHandleRecycleItem(pItem);
		pItem.setVisible(false);
		pItem.setIgnoreUpdate(true);
		pItem.clearEntityModifiers();
		pItem.clearUpdateHandlers();
	}
}
