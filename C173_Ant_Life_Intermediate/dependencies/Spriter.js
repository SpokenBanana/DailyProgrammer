function SpriterAtlas(jsonArray) {
	this.srcArray = jsonArray;
	this.spriteSheetLocation = jsonArray.meta.image;

	this.SpriteSheet;

	this.getSprite = function(filename) {
		var foundSprite = false;
		for (var i = 0; i < this.srcArray.frames.length; i++) {
			if (this.srcArray.frames[i].filename == filename) {
				var wantedSprite = this.srcArray.frames[i];
				return {
					srcImage: this.SpriteSheet,
					srcx: wantedSprite.frame.x,
					srcy: wantedSprite.frame.y,
					srcwidth: wantedSprite.frame.w,
					srcheight: wantedSprite.frame.h,
					width: wantedSprite.frame.w,
					height: wantedSprite.frame.h, 
					scale: 1.0,
					draw: function(context, posx, posy) {
						context.drawImage(this.srcImage, this.srcx, this.srcy, this.srcwidth, this.srcheight, posx, posy, this.width * this.scale, this.height * this.scale);
					}
				};
			}
		}

		return null;
	};
};