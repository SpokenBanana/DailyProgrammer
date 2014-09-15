jQuery(document).ready(function($){
	var canvas = document.getElementById('canvas');
	var FPS = 30, timer;
	N = 40;
	canvas.width = CELLSIZE * N;
	canvas.height = CELLSIZE * N;
	var context = canvas.getContext('2d');
	var ant = new Ant(CELLSIZE * (N/2), CELLSIZE * (N/2));
	var board = [];

	document.getElementById('change').onclick = function change() {
		console.log('hlelo');
		var text = document.getElementById('ctext').value;
		if (!text || text.length > 5) return;
		TURNS.WHITE = (text.charAt(0) === 'l') ? true : false;
		TURNS.BLACK = (text.charAt(1) === 'l') ? true : false;
		TURNS.BLUE = (text.charAt(2) === 'l') ? true : false;
		TURNS.GREEN = (text.charAt(3) === 'l') ? true : false;
		TURNS.YELLOW = (text.charAt(4) === 'l') ? true : false;
	};
	document.getElementById('pause').onclick = function(){
		this.innerHTML = (PAUSE) ? "Pause" : "Play";
		PAUSE = !PAUSE;
	};

	function setUpBoard() {
		for (var i = 0; i < canvas.width / CELLSIZE; i++) {
			board.push([]);
			var b = board[i];
			for (var j = 0; j < canvas.height / CELLSIZE; j++){
				b.push(new Cell(i * CELLSIZE, j * CELLSIZE));
				if (ant.x === i * CELLSIZE && ant.y === j * CELLSIZE)
					ant.currentTile = b[j];
			}
		}
	}

	document.getElementById('reset').onclick = function reset() {
		board = [];
		ant = new Ant(CELLSIZE * (N / 2), CELLSIZE * (N / 2));
		setUpBoard();
		draw();
	};

	document.getElementById('fps').onchange = function(){
		clearTimeout(timer);
		FPS = parseInt(this.value);
		console.log(FPS);
		timer = setInterval(tick, 1000/FPS);
		document.getElementById('num').innerHTML = "FPS : " + FPS.toString();
	};

	function drawBoard() {
		board.forEach(function(i){
			i.forEach(function(j){
				j.draw(context);
			});
		});
	}

	function findCurentTile() {
		for (var i = 0; i < canvas.width / CELLSIZE; i++) {
			var b = board[i];
			for (var j = 0; j < canvas.height / CELLSIZE; j++){
				if (ant.x === i * CELLSIZE && ant.y === j * CELLSIZE)
					ant.currentTile = b[j];
			}
		}
	}

	function update(){
		var color = ant.currentTile.colorStack[0];
		if (TURNS[color])
			ant.direction = (ant.direction + 1) % 4;
		else
			ant.direction = (((ant.direction - 1) % 4) + 4) % 4;
		ant.currentTile.flip();
		ant.move();
		findCurentTile();
	}

	function draw(){
		drawBoard();
		ant.draw(context);
	}
	function tick() {
		if (!PAUSE) {
			update();
			draw();
		}
	}
	setUpBoard();
	timer = setInterval(tick, 1000/FPS);
});