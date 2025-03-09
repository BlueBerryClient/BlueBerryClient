#version 150

uniform vec2 position; // Top-left corner
uniform vec2 size;     // Width and height
uniform float radius;  // Corner radius
uniform vec4 color;    // RGBA color

out vec4 fragColor;

void main() {
    vec2 p = gl_FragCoord.xy - (position + radius);
    vec2 rectSize = size - 2.0 * radius;

    float distance = max(abs(p.x) - rectSize.x / 2.0, abs(p.y) - rectSize.y / 2.0);
    distance -= radius;

    float alpha = smoothstep(0.0, 1.0, -distance);
    fragColor = vec4(color.rgb, color.a * alpha);
}
