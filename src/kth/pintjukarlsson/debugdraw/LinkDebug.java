package kth.pintjukarlsson.debugdraw;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;

public class LinkDebug {
	private Mesh line = new Mesh(true, 2, 0, new VertexAttribute(Usage.Position,
			3, "a_position"), new VertexAttribute(Usage.ColorPacked, 4,
			"a_color"));

	public LinkDebug(float x1, float y1, float x2, float y2, int c){
		line.setVertices(new float[] {
	            x1/2, y1/2, 0, Color.toFloatBits(200, c, 200, 255),
	            x2/2, y2/2, 0, Color.toFloatBits(200,c, 200, 255) });
	}
	public void Draw(){
		line.render(GL10.GL_LINE_STRIP);
	}
}
