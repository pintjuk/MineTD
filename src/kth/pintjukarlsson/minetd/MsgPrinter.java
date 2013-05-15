package kth.pintjukarlsson.minetd;

import java.util.ArrayList;
import java.util.Iterator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MsgPrinter {

	private static BitmapFont font = new BitmapFont();
	private static ArrayList<Message> messages = new ArrayList<>();
	private final static int MAX_MSGS = 10;
	
	private static class Message {
		public String text;
		public float duration;
		public float elapsed;
		
		public Message(String text, float duration) {
			this.text = text;
			this.duration = duration;
			elapsed = 0;
		}
		public String toString() {
			return text;
		}
	}

	public static void print(String text, float duration) {
		Message msg = new Message(text, duration);
		messages.add(msg);
	}
	
	public static void act(float deltatime) {
		while (messages.size() > MAX_MSGS) {
			messages.remove(0);
		}
		Iterator<Message> i = messages.iterator();
		while (i.hasNext()) {
			Message msg = i.next();
			msg.elapsed += deltatime;
			if (msg.elapsed >= msg.duration) {
				i.remove();
			}
		}
	}
	public static void Draw(SpriteBatch batch) {
		float x = 5;
		float y = Gdx.graphics.getHeight()-5;
		for (Message msg : messages) {
			float alpha = Math.abs(-1 + msg.elapsed/msg.duration);
			float alpha2 = (float)(Math.log((alpha*1000+1))/Math.log(1000));
			font.setColor(0.0f, 0.0f, 1.0f, alpha2);
			batch.begin();
			font.draw(batch, msg.text, x, y);
			batch.end();
			y -= 20;
		}
	}
}
