#version 150

in vec2 position;   // Vertex position
in vec2 texCoord;   // Texture coordinates

out vec2 fragTexCoord; // Pass texture coordinates to fragment shader

void main() {
    fragTexCoord = texCoord;
    gl_Position = vec4(position, 0.0, 1.0); // Convert to clip space
}
