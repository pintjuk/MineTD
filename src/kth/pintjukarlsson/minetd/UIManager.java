package kth.pintjukarlsson.minetd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Contains the Graphical User Interface.
 * 
 * (This class is a provider for the UIService interface.)
 * @author Markus Karlsson
 *
 */
public class UIManager implements UIService {
	
	private Skin skin;
	private Stage stage;
	private SpriteBatch batch;
	private float h;
	
	/**
	 * {@inheritDoc UIService}
	 */
	@Override
	public void init() {
		float w = Gdx.graphics.getWidth();
		h = 100;

		batch = new SpriteBatch();
		stage = new Stage(w, h, true, batch);
		((InputMultiplexer)Gdx.input.getInputProcessor()).addProcessor(stage);
		
		// A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
		// recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.
		skin = new Skin(Gdx.files.internal("data/style/uiskin.json"));
		
//		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
//		pixmap.setColor(Color.GRAY);
//		pixmap.fill();
//		skin.add("gray",  new Texture(pixmap));
		// Create a table along the bottom of the screen. Everything else will go inside this table.
		Table table = new Table();
		table.setBounds(0, 0, w, h);
		table.setBackground("white");
		System.out.println("Table is visible: " + table.isVisible());
		stage.addActor(table);
		
		
		// Create a button with the "default" TextButtonStyle.
		final TextButton button = new TextButton("New Game", skin);
		table.add(button);
		
		// Add a Listener to the button. ChangeListener is fired when the button's checked
		// state changes. For example, when clicked, Button#setChecked() is called,
		// or via a key press. If the event.cancel() is called, the checked state will
		// be reverted. ClickListener could have been used, but would only fire when clicked.
		// Also, canceling a ClickListener event won't revert the checked state.
		button.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("Clicked! Is checked: " + button.isChecked());
				//button.setText("Good job!");
			}
		});
		
		//Game game = new Game();
	}
	
	/**
	 * {@inheritDoc UIService}
	 */
	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, h, true);
	}
	
	/**
	 * {@inheritDoc UIService}
	 */
	@Override
	public void Draw() {
		//Gdx.gl.glClearColor(0.2f,  0.2f,  0.2f,  1);
		//Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		
		//Table.drawDebug(stage); // This is optional, but enables debug lines for tables.
	}
	
	/**
	 * 
	 * @return
	 */
	public int getSelection() {
		// TODO
		return 0;
	}
	
	/**
	 * {@inheritDoc UIService}
	 */
	@Override
	public boolean needsGL20() {
		return false;
	}

	/**
	 * {@inheritDoc UIService}
	 */
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * {@inheritDoc UIService}
	 */
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * {@inheritDoc UIService}
	 */
	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
		
	}
	

}
