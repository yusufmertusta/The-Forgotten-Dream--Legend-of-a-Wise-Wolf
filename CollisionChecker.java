package java_game;

import java.awt.Rectangle;
import java.io.Serializable;

public class CollisionChecker implements Serializable {
	private static final long serialVersionUID = 1L;

	GamePanel gp;

	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}

	public void checkTile(Entity entity) {
		Rectangle entityBounds = entity.getHitbox();
		int entityCol = entityBounds.x / gp.tileSize;
		int entityRow = entityBounds.y / gp.tileSize;

		if (entity.worldY < 0)
			gp.player.jumpSpeed = 0;

		for (int col = entityCol - 1; col <= entityCol + 1; col++) {
			for (int row = entityRow - 1; row <= entityRow + 1; row++) {
				if (col >= 0 && col < gp.maxScreenCol && row >= 0 && row < gp.maxScreenRow) {
					int tileNum = gp.tileM.mapTileNum[col][row];
					Tile tile = gp.tileM.tile[tileNum];
					if (tile.collision) {
						Rectangle tileBounds = new Rectangle(col * gp.tileSize, row * gp.tileSize, gp.tileSize,
								gp.tileSize);
						if (entityBounds.intersects(tileBounds)) {
							if (entity.worldX + entityBounds.width + entity.speed > tileBounds.x
									&& entity.worldX < tileBounds.x) {
							} else if (entity.worldX < tileBounds.x + tileBounds.width
									&& entity.worldX + entityBounds.width > tileBounds.x + tileBounds.width) {
							}
							if (entity.worldY + entityBounds.height > tileBounds.y && entity.worldY < tileBounds.y) {
								entity.worldY = tileBounds.y - entityBounds.height;
								entity.jumping = false;

							} else if (entity.worldY < tileBounds.y + tileBounds.height
									&& entity.worldY + entityBounds.height > tileBounds.y + tileBounds.height) {
								gp.player.jumpSpeed = 0;
								entity.worldY = tileBounds.y + tileBounds.height;

							}
						}
					}
				}
			}
		}
	}

}
