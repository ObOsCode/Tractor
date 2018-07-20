package tractorPlatform.tractor.controllers;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import javax.swing.JFrame;

import tractorPlatform.tractor.models.Tractor;
import view.MainWindow;

public class KeyboardController
{
	
	private Tractor _tractor;
	
	private boolean _isEnabled = false;
	
	private int _powerValue = 0;
	private int _turnValue = 0;
	
	private final int _INCREMENT_POWER_VALUE = 5;
	private final int _INCREMENT_TURN_VALUE = 10;
	
	private final int _DECREMENT_POWER_VALUE = 10;
	private final int _DECREMENT_TURN_VALUE = 20;
	
	
	HashMap<Integer, Boolean> _pressedKeys = new HashMap<Integer, Boolean>();
	
	
	public KeyboardController(Tractor tractor, MainWindow window)
	{
		this._tractor = tractor;
		
		JFrame mainWindowFrame = window.getFrame();
		
		_pressedKeys.put(KeyEvent.VK_LEFT, false);
		_pressedKeys.put(KeyEvent.VK_RIGHT, false);
		_pressedKeys.put(KeyEvent.VK_DOWN, false);
		_pressedKeys.put(KeyEvent.VK_UP, false);
		
		mainWindowFrame.setFocusable(true);
		
//		mainWindowFrame.removeKeyListener(l);

		mainWindowFrame.addKeyListener(new KeyAdapter()
		{
			
			@Override
			public void keyPressed(KeyEvent e)
			{
				_pressedKeys.replace(e.getKeyCode(), true);
			}
			
			
			@Override
			public void keyReleased(KeyEvent e)
			{
				_pressedKeys.replace(e.getKeyCode(), false);
			}
		});
		

	}
	
	public boolean isEnabled()
	{
		return _isEnabled;
	}
	
	
	public void setEnabled(boolean value)
	{
		_isEnabled = value;
	}
	
	
			///////////////////
			/////Private///////
			///////////////////
	
	
	public void update()
	{
		
		if(!_isEnabled)
		{
			return;
		}
		
		if(_pressedKeys.get(KeyEvent.VK_LEFT))
		{
			addTurn(-_INCREMENT_TURN_VALUE);
		}else if(_pressedKeys.get(KeyEvent.VK_RIGHT))
		{
			addTurn(_INCREMENT_TURN_VALUE);
		}else
		{
			decrementTurn();
		}
		
		
		if(_pressedKeys.get(KeyEvent.VK_UP))
		{
			addPower(_INCREMENT_POWER_VALUE);
			
		}else if(_pressedKeys.get(KeyEvent.VK_DOWN))
		{
			addPower(-_INCREMENT_POWER_VALUE);
		}else
		{
			decrementPower();
		}

		_tractor.setTurn(_turnValue);
		_tractor.setPower(_powerValue);
		
	}
	
	
	private void addPower(int addValue)
	{
		int newValue = _powerValue + addValue;
		
		if(newValue>100)
		{
			_powerValue = 100;
			return;
		}
		
		if(newValue<-100)
		{
			_powerValue = -100;
			return;
		}
		_powerValue += addValue;
	}
	
	
	private void decrementPower()
	{
		if(_powerValue==0)
		{
			return;
		}
		
		if(Math.abs(_powerValue)<_DECREMENT_POWER_VALUE)
		{
			_powerValue = 0;
			return;
		}
		
		if(_powerValue>0)
		{
			_powerValue-=_DECREMENT_POWER_VALUE;
		}
		
		if(_turnValue<0)
		{
			_powerValue+=_DECREMENT_POWER_VALUE;
		}
	}
	
	
	private void decrementTurn()
	{
		if(_turnValue==0)
		{
			return;
		}
		
		if(Math.abs(_turnValue)<_DECREMENT_TURN_VALUE)
		{
			_turnValue = 0;
			return;
		}
		
		if(_turnValue>0)
		{
			_turnValue-=_DECREMENT_TURN_VALUE;
		}
		
		if(_turnValue<0)
		{
			_turnValue+=_DECREMENT_TURN_VALUE;
		}
	}
	
	
	private void addTurn(int addValue)
	{
		int newValue = _turnValue + addValue;
		
		if(newValue>100)
		{
			_turnValue = 100;
			return;
		}
		
		if(newValue<-100)
		{
			_turnValue = -100;
			return;
		}
		_turnValue += addValue;
	}
	
}
