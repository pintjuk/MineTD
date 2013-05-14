package kth.pintjukarlsson.minetd;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
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
	private ArrayList<TextButton> buttons;
	private MineTD game;
	
	private Label numEnemiesLabel;
	private TextButton waveButton;
	
	private float h; // ui height
	private float w; // ui width
	private int wave; // counter for enemy waves
	
	public UIManager(MineTD game) {
		this.game = game;
	}
	/**
	 * {@inheritDoc UIService}
	 */
	@Override
	public void init() {
		// set stage/table location and size
		float x = Gdx.graphics.getWidth()-100;
		float y = 0;
		w = 100;
		h = Gdx.graphics.getHeight();

		// initialize fields
		wave = 1;
		batch = new SpriteBatch();
		stage = new Stage(w, h, true, batch);
		buttons = new ArrayList<>();
		((InputMultiplexer)Gdx.input.getInputProcessor()).addProcessor(stage);
		
		// A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
		// recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.
		skin = new Skin(Gdx.files.internal("data/style/uiskin.json"));
		
		// Create a table. Everything else will go inside this table.		
		Table table = new Table(skin);
		table.setBounds(x, y, w, h);
		table.setBackground("gray");
		stage.addActor(table);
		
		// Create wave info / controls
		final Label waveLabel = new Label("Wave: " + wave, skin);
		table.add(waveLabel).top();
		table.row();
		numEnemiesLabel = new Label("Enemies: " + game.getEnemiesManager().getEnemies().size(), skin);
		table.add(numEnemiesLabel).top();
		table.row();
		waveButton = new TextButton("Start Wave", skin);
		skin.setEnabled(waveButton, false);
		table.add(waveButton).expand().top();
		table.row();
		
		waveButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (game.getEnemiesManager().spawnWave()) {
					wave++;
					waveLabel.setText("Wave: " + wave);
					skin.setEnabled(waveButton, false);
				}
			}
		});
		
		
		// Create buttons for each TileType using the "default" TextButtonStyle.
		for (TileType tt : TileType.values()) {
			buttons.add(new TextButton(tt.toString(), skin));
		}
		for (TextButton button : buttons) {
			table.add(button).width(80);
			table.row();
			// Add a Listener to the button. ChangeListener is fired when the button's checked
			// state changes. For example, when clicked, Button#setChecked() is called,
			// or via a key press. If the event.cancel() is called, the checked state will
			// be reverted. ClickListener could have been used, but would only fire when clicked.
			// Also, canceling a ClickListener event won't revert the checked state.
			button.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					// TODO - select tile type to place + visual indication
					//System.out.println("Clicked! Is checked: " + button.isChecked());
					//button.setText("Good job!");
				}
			});
		}
		table.getCell(buttons.get(buttons.size()-1)).expand().top();
		
	}
	
	/**
	 * {@inheritDoc UIService}
	 */
	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, h, true);
	}
	
	public void Update() {
		int numEnemies = game.getEnemiesManager().getEnemies().size(); 
		numEnemiesLabel.setText("Enemies: " + numEnemies);
		if (numEnemies == 0)
			skin.setEnabled(waveButton, true);
		
		stage.act(Gdx.graphics.getDeltaTime());
	}
	/**
	 * {@inheritDoc UIService}
	 */
	@Override
	public void Draw() {
		stage.draw();
		Table.drawDebug(stage); // This is optional, but enables debug lines for tables.
	}
	
	/**
	 * 
	 * @return
	 */
	public int getSelection() {
		// TODO
		return 0;
	}
	
	public float getHeight() {
		return h;
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
