const canvas = document.getElementById('liquidCanvas');
const ctx = canvas.getContext('2d');

canvas.width = window.innerWidth;
canvas.height = window.innerHeight;


let particlesArray = [];

// Manejar el tamaño del canvas en caso de redimensionamiento
window.addEventListener('resize', function () {
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;
    init();
});

// Crear partículas de burbujas
class Particle {
    constructor(x, y, size, speedX, speedY) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.speedX = speedX;
        this.speedY = speedY;
        this.color = this.randomColor();
    }

    randomColor() {
        const colors = ['#FF5733', '#FFC300', '#DAF7A6', '#FF33F6', '#33FFF6', '#7D3C98'];
        return colors[Math.floor(Math.random() * colors.length)];
    }

    update() {
        this.x += this.speedX;
        this.y += this.speedY;

        if (this.x + this.size > canvas.width || this.x - this.size < 0) {
            this.speedX = -this.speedX;
        }

        if (this.y + this.size > canvas.height || this.y - this.size < 0) {
            this.speedY = -this.speedY;
        }
    }

    draw() {
        ctx.fillStyle = this.color;
        ctx.beginPath();
        ctx.arc(this.x, this.y, this.size, 0, Math.PI * 2);
        ctx.closePath();
        ctx.fill();
    }
}

// Inicializar partículas
function init() {
    particlesArray = [];
        for (let i = 0; i < 100; i++) {
            let size = Math.random() * 20 + 10;
            let x = Math.random() * (canvas.width - size * 2) + size;
            let y = Math.random() * (canvas.height - size * 2) + size;
            let speedX = (Math.random() - 0.5) * 2;
            let speedY = (Math.random() - 0.5) * 2;
            particlesArray.push(new Particle(x, y, size, speedX, speedY));
        }
}

// Animar partículas
function animate() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    particlesArray.forEach(particle => {
        particle.update();
        particle.draw();
    });

    requestAnimationFrame(animate);
}

init();
animate();
