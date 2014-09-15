var UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3, CELLSIZE = 10, PAUSE=false;
var WHITE = '#FFFFFF', BLACK = '#000000', BLUE = '#39A8EA', GREEN = '#2EF728', YELLOW = '#F2F836';
var TURNS = {
	'#FFFFFF': true,
	'#000000': false,
	'#39A8EA': false,
	'#2EF728': true,
	'#F2F836': true
};
function Ant(x, y){
	this.x = x;
	this.y = y;
	this.currentTile = null;
	this.direction = UP;
	this.move = function(){
		if (this.direction === UP) {
			this.y -= CELLSIZE;
		}
		else if (this.direction === DOWN) {
			this.y += CELLSIZE;
		}
		else if (this.direction === LEFT) {
			this.x -= CELLSIZE;
		}
		else if (this.direction === RIGHT) {
			this.x += CELLSIZE;
		}
	};
	this.draw = function(context){
		context.fillStyle = '#FE0101';
		context.fillRect(this.x + (CELLSIZE / 4) , this.y + (CELLSIZE / 4), CELLSIZE / 2, CELLSIZE / 2);
	};
}

function Cell(x, y) {
	this.x = x;
	this.y = y;
	this.color = WHITE;
	this.colorStack = [WHITE, BLACK, BLUE, GREEN, YELLOW];
	this.flip = function() {
		this.colorStack.unshift(this.colorStack.pop());
	};
	this.draw = function(context) {
		context.fillStyle = this.colorStack[0];
		context.fillRect(this.x, this.y, CELLSIZE,CELLSIZE);
	};
}