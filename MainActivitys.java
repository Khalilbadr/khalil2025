package com.tas.Tasabeeh;
//package com.kh.acounts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;

import android.widget.Toast;
import com.tas.Tasabeeh.R;
import com.google.android.material.navigation.NavigationView;
import android.content.Intent;
//import com.adapter.database.DB_Adapter;
import android.database.Cursor;
import android.widget.SearchView;
//import android.widget.SearchView.OnQueryTextListener;
//import androidx.appcompat.widget.SearchView.OnQueryTextListener;
//import androidx.appcompat.widget.SearchView;
import android.widget.ListView;
import android.widget.AdapterView;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

//port com.tas.Tasabeeh.CategoryItem;

 

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tas.adapter.database.CursorAdapter;
import android.view.MenuItem;
import android.view.Menu;
import android.view.MenuInflater;
import com.tas.adapter.database.DB_Adapter;
import com.tas.Notificationy.NotificationSettings;
 
import android.net.MailTo;
 
import com.tas.search.ViewPagers;
import androidx.appcompat.app.ActionBar;
import com.tas.adapter.database.CursorAdapters;
import com.tas.MainLight;
 
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import com.tas.constants.ApplicationConstants;
import java.util.Locale;
import android.widget.ImageView;
import android.view.Gravity;
import android.text.TextUtils;
import android.widget.Switch;
import android.widget.CompoundButton;
import android.content.res.Configuration;
import androidx.core.view.GravityCompat;
import com.tas.search.SearchTas;
import com.tas.Notificationy.seek.NotificationSetting;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tas.Tasabeeh.ui.InsertActivity;
import com.tas.Tasabeeh.ui.userActivity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import org.xmlpull.v1.XmlPullParser;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import com.tas.Tasabeeh.CategoryItem.type;
import com.tas.constants.ApplicationConstants.Direction;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import com.tas.arabic.*;
import com.tas.Trans.TransActivity;
import com.tas.Trans.TransActivitys;
import com.tas.Trans.MainActivity;
//spread_inside
public class MainActivitys extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener ,SearchView.OnQueryTextListener{
	private Locale currentLocale;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    ListView grid1;
    SharedPreferences preferencess;
    int currentThemes;
    int catId;
	//OnQueryTextListener l;
	private Switch switchMode;
    private View selectedItem;
    ListView gvTasabeh = null;
    private SearchView mSearchView;
    private static final String PREFS_NAME = "theme_prefs";
    private static final String CURRENT_THEME_KEY = "current_theme";
    private Cursor tasabeehCursor;
	private LogItemCursor adapter;
	//   ListAdapter adapter ;
    private CursorAdapters adapters;
    Button  btnToggle;
    ;TextView txtMode,balance,name;
    private Cursor azkarCursor;
	 
	NavigationView navigationView;
	private ImageView img_menu,customer_image;

	private ImageView imgNativeShare;

    public View selectedTasbeha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
		
        setContentView(R.layout.dasch);
        Utils.setLocale(getBaseContext());
	//	getSupportActionBar().hide();
		mSearchView = (SearchView) findViewById(R.id.search_view);
		grid1= (ListView) findViewById(R.id.list_view);
		navigationView = findViewById(R.id.navigationView);
		mDrawerLayout = findViewById(R.id.drawer);
		
		View hView =  navigationView.getHeaderView(0);
		
		//customer_image = hView.findViewById(R.id.avatar);
		balance = hView.findViewById(R.id.balance);
		name = hView.findViewById(R.id.name);
//	//	edit_button = hView.findViewById(R.id.avatar);
		//navigationDrawer();
		DB_Adapter.Init(this);

		initializeConstants();
		initializeAddButtonHandler();
		//initializeNativeShareListener();
		//initializeNativeShareIconHandler();
		NahNaheri();
		//setupDrawer();
		navigationDrawer();
		//mDrawe();
		
		final BottomNavigationView bottom_nav = findViewById(R.id.bottom_navigation);
        bottom_nav.setOnNavigationItemSelectedListener(navListener);
        
		
		this.azkarCursor = DB_Adapter.select_all("","");
		adapter = new LogItemCursor(this, this.azkarCursor);

		grid1.setAdapter(adapter);		
		//grid1.setAdapter(new LogItemCursor(this, this.azkarCursor));		
		// android.R.layout.simple_list_item_1,	
		this.grid1.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> adapterView, View view, int arg2, long arg3) {
				MainActivitys.this.startsearchActivity(view.getTag().toString());
					 
				}
			});
		//تصفير العدد
		grid1.setOnItemLongClickListener(new OnItemLongClickListener() {
				public boolean onItemLongClick(AdapterView<?> adapterView, View view, int arg2, long arg3) {
					int i;
					if (view.getTag() != null ){
						if(  azkarCursor.getString(azkarCursor.getColumnIndex("CAT_TYPE")).equals("T")){
							i=1; }
						else  {i=2;}

						MainActivitys.this.catId = Integer.parseInt(view.getTag().toString());
						MainActivitys.this.selectedItem = view;
						MainActivitys.this.showDialog(i); 

					}

					return true;
				}
			});
		
		grid1.setTextFilterEnabled(true);
		setupSearchView();
		//Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        }
		
	private void setupDrawers() {

		mDrawerLayout = findViewById(R.id.drawer);
		//mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);

		if (mDrawerLayout.isDrawerOpen(navigationView)) {
			mDrawerLayout.closeDrawer(navigationView);
		} else {
			mDrawerLayout.openDrawer(navigationView);
			}
		//NavigationView navigationView = findViewById(R.id.navigationView);
       navigationView.setNavigationItemSelectedListener(this);
	}
	private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
		//navigationView.setCheckedItem(R.id.nav_share);
        navigationView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
						mDrawerLayout.closeDrawer(GravityCompat.START);
					} else {
						mDrawerLayout.openDrawer(GravityCompat.START);
					}
				}
			});

	}
	private void setupDrawer() {
		
		mDrawerLayout = findViewById(R.id.drawer);
		//mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);

//					// فتح الدرج
//					if (!mDrawerLayout.isDrawerOpen(Gravity.START)) {
//						mDrawerLayout.openDrawer(Gravity.START);
//					}
		 
        navigationView.setNavigationItemSelectedListener(this);
				}
			
	
		
         
	 private void  mDrawe(){
		
		
		mDrawerLayout = findViewById(R.id.drawer);

        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
    }
		
	private void startTasabeehActivity(String TasbeehID) {
        Intent intent = new Intent(this, TasabeehActivity.class);
        Bundle bundle = new Bundle();    bundle.putString("Tasbeeh_Id", TasbeehID);
        intent.putExtras(bundle);
        startActivity(intent);
        }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
			startActivity(item.getIntent());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
	//public boolean onNavigationItemSelected(@NonNull MenuItem item)
       
		Intent intent;
		switch(item.getItemId()){
        case R.id.mi_help:
             intent=new Intent(getApplicationContext(),HelpActivity.class);
			startActivity(intent);
				break;
			case R.id.mi_scores:
				intent=new Intent(getApplicationContext(),LogActivity.class);
				startActivity(intent);
				break;
			case R.id.mi_settings:
			
				intent=new Intent(getApplicationContext(),SettingsActivity.class);
            startActivity(intent);
             break;
            case R.id.mi_trans:

                intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                break;
		case R.id.mi_night:
			 intent=new Intent(getApplicationContext(),SearchTas.class);
            startActivity(intent);
		break;
			//startPrayersActivity(SearchTas.class);
             
		case R.id.mi_notfition:
				intent=new Intent(getApplicationContext(),com.tas.Notificationy.seek.NotificationSettings.class);
            startActivity(intent);
            break; 
		case R.id.mi_aboutUs:
				intent=new Intent(getApplicationContext(),AboutActivity.class);
            startActivity(intent);
			break;
		case R.id.notifications_settings :
			intent = new Intent(MainActivitys.this, NotificationSettings.class);
			startActivity(intent);
			break;
		case R.id.nav_settings :
                intent = new Intent(MainActivitys.this, userActivity.class);
			startActivity(intent);
			break;
	case R.id.add_tas :
				intent = new Intent(MainActivitys.this, InsertActivity.class);
		startActivity(intent);
		break;
		}
		mDrawerLayout.closeDrawer(GravityCompat.START);
		return true;
	}
	
	
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

         //   Fragment selectedFragment = null;
            switch (menuItem.getItemId()) {
                case R.id.tasall:
					grid1.setAdapter(new LogItemCursor(getBaseContext(),DB_Adapter.select_serach("")));
				//adapter.notifyDataSetChanged();
				
//                 grid1.setOnItemClickListener(new OnItemClickListener() {
//                            public void onItemClick(AdapterView<?> adapterView, View view, int arg2, long arg3) {
//                                MainActivitys.this.startsearchActivity(view.getTag().toString());
//
//                            }
//                        });
                        break;
                case R.id.zaker:
					//grid1.setAdapter(new LogItemCursor(getBaseContext(),DB_Adapter.selectCategoryLoging("'A'")));
                    MainActivitys.this.showDialog(0);
                    //selectedFragment = new ContentFragment();
                   // status_bar_text.setText(R.string.settings);
                    break;
                case R.id.dayNigthMode:
					grid1.setAdapter(new LogItemCursor(getBaseContext(),DB_Adapter.selectCategoryLoging("'T'")));
//					grid1.setOnItemClickListener(new OnItemClickListener() {
//                            public void onItemClick(AdapterView<?> adapterView, View view, int arg2, long arg3) {
//                                MainActivitys.this.startTasabeehActivity(view.getTag().toString());
//
//                            }
//                        });
                    
                    //  if (id == R.id.nav_settings) {
					 
                    break;
					
				case R.id.other:

					//  if (id == R.id.nav_settings) {
					grid1.setAdapter(new LogItemCursor(getBaseContext(),DB_Adapter.selectCategoryLoging("'B'")));
					
                    break;
					
            }
			
         //   getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, selectedFragment).commit();
            return true;
        }
    };

	public void onRecordClick(int p) {

		Intent i = new Intent(this, ViewPagers.class);
		i.putExtra("CAT_ID",p);
		startActivity(i);

		//onListItemClick(p);
    }
	 


	public void onRecordClicks(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
		//i.putExtra("CAT_ID",((int)var4));
		startActivity(intent);
    }

//	public boolean onOptionsItemSelected(MenuItem item) {
//
//		switch(item.getItemId()){
//
//
//			case R.id.dayNigthMode:
//				Toast.makeText(this, "Correct!", Toast.LENGTH_LONG).show();
//		}
//
//
//
//		return true;
//	}

	private void startPrayersActivity(Class cat) {
        Intent intent = new Intent(this, cat);
        Bundle bundle = new Bundle();
		//  bundle.putString("CAT_ID", categoryID);
        intent.putExtras(bundle);
        startActivityForResult(intent,0);
    }

	protected void onListItemClick(  int var3) {
		//super.onListItemClick(var1, var2, var3, var4);
		Intent i = new Intent(this, MainPagers.class);
		this.azkarCursor.moveToPosition(var3);


		//i.putExtra("CAT_ID",((int)var4));
		String var9;

		var9 = this.azkarCursor.getString(this.azkarCursor.getColumnIndex("CAT_NAME"));

		i.putExtra("CAT_NAME", var9);
		this.startActivity(i);
	}
	private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
 
        mSearchView.setSubmitButtonEnabled(false);
        mSearchView.setQueryHint(getString(R.string.searchT));
		 
    }

    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            grid1.clearTextFilter();
        } else {
			String text = newText;

			azkarCursor = DB_Adapter.select_serach(text);

			grid1.setAdapter(new LogItemCursor(this, this.azkarCursor));
//			this.grid1.setOnItemClickListener(new OnItemClickListener() {
//					public void onItemClick(AdapterView<?> adapterView, View view, int arg2, long arg3) {
			//SearchTas.this.startsearchActivity(view.getTag().toString());
			//}
			//});
			DB_Adapter.close();
        }
        return true;
    }
	public boolean onQueryTextSubmit(String query) {
        return false;
    }
	private void NahNaheri(){

		switchMode = findViewById(R.id.switch_modes);

		//switchTheme = findViewById(R.id.switch_modes);

        // تعيين حالة المفتاح بناءً على الثيم المحفوظ
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int currentTheme = preferences.getInt(CURRENT_THEME_KEY, R.style.AppTheme);

		switchMode.setChecked(currentTheme == R.style.AppThemeNight);

        switchMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					toggleTheme(isChecked);
				}
			});
	}

	private void toggleTheme(boolean isNight) {
        int newTheme = isNight ? R.style.AppThemeNight : R.style.AppTheme;
        setAppTheme(newTheme);
    }
	
	private void initializeConstants() {
		this.currentLocale = ApplicationConstants.CURRENT_LOCALE;
	}
	
	
	private void initializeNativeShareIconHandler() {
        this.imgNativeShare = (ImageView) findViewById(R.id.imgNateShare);
        this.imgNativeShare.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					try {
						Intent sharingIntent = new Intent("android.intent.action.SEND");
						sharingIntent.setType("text/plain");
						String shareBody = MainActivitys.this.getResources().getString(R.string.azkariNativeShareingBody);
						sharingIntent.putExtra("android.intent.extra.SUBJECT", MainActivitys.this.getResources().getString(R.string.appNamedata));
						sharingIntent.putExtra("android.intent.extra.TEXT", shareBody);
						MainActivitys.this.startActivity(Intent.createChooser(sharingIntent, MainActivitys.this.getResources().getString(R.string.azkariShareingVia)));
						//SearchTas.this.wasPosted = Boolean.valueOf(true);
					} catch (Exception e) {
//						if (!Utils.isOnline(MenList.this)) {
//							MenList.this.wasPosted = Boolean.valueOf(false);
//							//MenList.this.applicationHandler.post(MenList.this.notificationRunnable);
//						}
					}
				}
			});
    }
	
	 
	private void initializeAddButtonHandler() {

//		this.btnAdd = (ImageView) findViewById(R.id.btnCustom);
//		// this.btnAdd.setBackgroundResource(R.drawable.addtasbehaicon);
//        this.btnAdd.setOnClickListener(new View.OnClickListener() {
//				public void onClick(View v) {
//					SearchTas.this.showDialog(0);
//				}
//			});

        this.img_menu = (ImageView) findViewById(R.id.imgMenu1);
		//this.registerForContextMenu(img_menu);
		this.img_menu.setOnClickListener(new View.OnClickListener() {
				public void onClick(View arg0) {
//					NavigationView navigationView = findViewById(R.id.navigationView);
//					navigationView.setNavigationItemSelectedListener(MainActivitys.this);
					//mToggle.onDrawerOpened(g);  
									if (!mDrawerLayout.isDrawerOpen(Gravity.START)) {
				mDrawerLayout.openDrawer(Gravity.START);
					}
					
				}
			});
//        ImageView img_menu2 = (ImageView) findViewById(R.id.help_btn);
//        registerForContextMenu(img_menu2);
//        img_menu2.setOnClickListener(new View.OnClickListener() {
//				public void onClick(View arg0) {
//					arg0.showContextMenu();
//				}
//			});


    }
	
	private void startsearchActivity(String ID) {
		String ci;
		Class ii;
        if (azkarCursor.getString(azkarCursor.getColumnIndex("CAT_TYPE")).equals("T")) {


			ci="Tasbeeh_Id";
			ii=TasabeehActivity.class;
            Intent intent = new Intent(this, ii);
            Bundle bundle = new Bundle();
            bundle.putString("Tasbeeh_Id", ID);
            intent.putExtras(bundle);
            startActivity(intent);

		}else{


			ci="CAT_ID";
			ii=MainPagers.class;




			Intent intent = new Intent(this, ii);
			Bundle bundle = new Bundle();
			bundle.putString(ci, ID);
			intent.putExtras(bundle);
			startActivity(intent);
		}
	}
	public void onBackPressed() {
        super.onBackPressed();
    }
    
    public void onDestroy() {
        super.onDestroy();
      //  this.stopCheckingUpdate = Boolean.valueOf(true);
    }

    /* Access modifiers changed, original: protected */
    public void onPause() {
        super.onPause();
       // this.stopCheckingUpdate = Boolean.valueOf(true);
    }

    /* Access modifiers changed, original: protected */
    public void onStop() {
        super.onStop();
        //this.stopCheckingUpdate = Boolean.valueOf(true);
    }

    /* Access modifiers changed, original: protected */
    public void onResume() {
        super.onResume();
        if (this.currentLocale != null && ApplicationConstants.CURRENT_LOCALE != null) {
            if (!this.currentLocale.equals(ApplicationConstants.CURRENT_LOCALE) || ApplicationConstants.REFILL_GRIDS) {
                ApplicationConstants.REFILL_GRIDS = false;
                

                this.currentLocale = ApplicationConstants.CURRENT_LOCALE;
            }
        }
    }
   
    private Dialog getDialog(int dialogId) {
        LayoutParams pars = new LayoutParams(-2, -2);
        pars.weight = 50.0f;
        pars.gravity = 17;
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService("layout_inflater");

        switch (dialogId) {
            case 0:
                final View layout = layoutInflater.inflate(R.layout.add_new_tasbeeh_dialog, (ViewGroup) findViewById(R.id.root));
                TextView txvTasbeeha = (TextView) layout.findViewById(R.id.tv_newTasbeeh);
                final EditText edt = (EditText) layout.findViewById(R.id.EditText_newTasbeeh);
                Utils.setText(txvTasbeeha, getString(R.string.tasabeh_title_dialog));
                Builder builder = new Builder(this);
                builder.setView(layout);
                builder.setTitle(XmlPullParser.NO_NAMESPACE);
                Button btnOk = new Button(this);
                Utils.setText(btnOk, getString(R.string.ok));
                btnOk.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            String tasbehaTxt = edt.getText().toString();
                            TextView txvRequiered;
                            if (tasbehaTxt.trim().length() > 0 && tasbehaTxt.trim().length() <= 100) {
                                MainActivitys.this.insertTasbeha(tasbehaTxt);
                                MainActivitys.this.removeDialog(0);
                                Utils.getToast(MainActivitys.this, MainActivitys.this.getString(R.string.succesfullyAdded), R.drawable.success, 0).show();
                            } else if (tasbehaTxt.trim().length() >= 50) {
                                txvRequiered = (TextView) layout.findViewById(R.id.txvRequiered);
                                txvRequiered.setGravity(17);
                                Utils.setText(txvRequiered, MainActivitys.this.getString(R.string.reachLimit));
                                txvRequiered.setVisibility(0);
                            } else {
                                txvRequiered = (TextView) layout.findViewById(R.id.txvRequiered);
                                txvRequiered.setGravity(17);
                                Utils.setText(txvRequiered, MainActivitys.this.getString(R.string.requiered));
                                txvRequiered.setVisibility(0);
                            }
                        }
                    });
                TableRow tblrowAddTasbeeha = (TableRow) layout.findViewById(R.id.tblrowAddTasbeeha);
                Button btnCancelAdd = new Button(this);
                Utils.setText(btnCancelAdd, getString(R.string.cancel));
                btnCancelAdd.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            MainActivitys.this.removeDialog(0);
                        }
                    });
                if (ApplicationConstants.APPLICATION_LANGUAGE_DIRECTION == Direction.LTR) {
                    tblrowAddTasbeeha.addView(btnOk, pars);
                    tblrowAddTasbeeha.addView(btnCancelAdd, pars);
                    txvTasbeeha.setGravity(3);
                } else {
                    tblrowAddTasbeeha.addView(btnCancelAdd, pars);
                    tblrowAddTasbeeha.addView(btnOk, pars);
                    txvTasbeeha.setGravity(5);
                }
                Dialog addDialog = builder.create();
                addDialog.setCancelable(true);
                addDialog.setCanceledOnTouchOutside(false);
                addDialog.setOnCancelListener(new OnCancelListener() {
                        public void onCancel(DialogInterface dialog) {
                            MainActivitys.this.removeDialog(0);
                        }
                    });
                return addDialog;
            case 1:
                View confirmLayout = layoutInflater.inflate(R.layout.delete_confirm_dialog, (ViewGroup) findViewById(R.id.deleteConfirmRoot));
                TextView txvConfirm = (TextView) confirmLayout.findViewById(R.id.tvConfirm);
                Utils.setText(txvConfirm, getString(R.string.confirmDeletion));
                Builder confirmBuilder = new Builder(this);
                confirmBuilder.setView(confirmLayout);
                confirmBuilder.setTitle(XmlPullParser.NO_NAMESPACE);
                confirmBuilder.setCancelable(true);
                TableRow tableRow = (TableRow) confirmLayout.findViewById(R.id.tableRow1);
                btnOk = new Button(this);
                Utils.setText(btnOk, getString(R.string.ok));
                btnOk.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            MainActivitys.this.deleteTasbeeha(MainActivitys.this.selectedTasbeha);
                            MainActivitys.this.removeDialog(1);
                        }
                    });
                Button btnCancel = new Button(this);
                Utils.setText(btnCancel, getString(R.string.cancel));
                if (ApplicationConstants.APPLICATION_LANGUAGE_DIRECTION == Direction.LTR) {
                    tableRow.addView(btnOk, pars);
                    tableRow.addView(btnCancel, pars);
                    txvConfirm.setGravity(3);
                } else {
                    tableRow.addView(btnCancel, pars);
                    tableRow.addView(btnOk, pars);
                    txvConfirm.setGravity(5);
                }
                btnCancel.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            MainActivitys.this.removeDialog(1);
                        }
                    });
                Dialog confirmDialog = confirmBuilder.create();
                confirmDialog.setCancelable(true);
                confirmDialog.setCanceledOnTouchOutside(false);
                confirmDialog.setOnCancelListener(new OnCancelListener() {
                        public void onCancel(DialogInterface dialog) {
                            MainActivitys.this.removeDialog(1);
                        }
                    });
                return confirmDialog;

            case 2:
                View resetDialogLayout = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.delete_confirm_dialog, (ViewGroup) findViewById(R.id.deleteConfirmRoot));
                Builder resetDialogBuilder = new Builder(this);
                //Builder confirmBuilder = new Builder(this);
                resetDialogBuilder.setView(resetDialogLayout);
                TextView tvDialogTitle = new TextView(this);
                tvDialogTitle.setTextSize(25.0f);
                tvDialogTitle.setGravity(17);
                Utils.setText(tvDialogTitle, getString(R.string.rseset_dialog_title));
                tvDialogTitle.setGravity(17);
                resetDialogBuilder.setCustomTitle(tvDialogTitle);
                TextView tvResetText = (TextView) resetDialogLayout.findViewById(R.id.tvConfirm);
                tvResetText.setGravity(5);
                Utils.setText(tvResetText, getString(R.string.reset_dialog_body));
                tvResetText.setLines(2);
                Button okBtn = new Button(this);
                Utils.setText(okBtn, getString(R.string.ok));
                okBtn.setOnClickListener(new OnClickListener() {
                        public void onClick(View arg0) {
                            TextView tvCount = (TextView) MainActivitys.this.selectedItem.findViewById(R.id.tvCount);
                            TextView tvPeriod = (TextView) MainActivitys.this.selectedItem.findViewById(R.id.tvPeriod);
                            tvCount.setTag(Boolean.valueOf(true));
                            tvCount.setText("0 ");
                            tvPeriod.setText("00:00:00 ");
                            DB_Adapter.reset_Categories_TimeAndCount(MainActivitys.this.catId);
                            MainActivitys.this.removeDialog(2);
                        }
                    });
                Button cancelBtn = new Button(this);
                Utils.setText(cancelBtn, getString(R.string.cancel));
                cancelBtn.setText(ArabicUtilities.reshape(getResources().getString(R.string.cancel)));
                cancelBtn.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            MainActivitys.this.removeDialog(2);
                        }
                    });
                TableRow row = (TableRow) resetDialogLayout.findViewById(R.id.tableRow1);
                if (ApplicationConstants.APPLICATION_LANGUAGE_DIRECTION == Direction.LTR) {
                    row.addView(okBtn, pars);
                    row.addView(cancelBtn, pars);
                    tvResetText.setGravity(3);
                    tvDialogTitle.setGravity(17);
                } else {
                    row.addView(cancelBtn, pars);
                    row.addView(okBtn, pars);
                    tvResetText.setGravity(5);
                    tvDialogTitle.setGravity(17);
                }
                Dialog resdDialog = resetDialogBuilder.create();
                resdDialog.setCancelable(true);
                resdDialog.setCanceledOnTouchOutside(false);
                resdDialog.setOnCancelListener(new OnCancelListener() {             
                        public void onCancel(DialogInterface dialog) {
                            MainActivitys.this.removeDialog(2);
                        }
                    });
                return resdDialog;



                //return resetDialogBuilder.create();
            default:
				return null;}}
    public Dialog onCreateDialog(int id) {
        return getDialog(id);
    }
    private void deleteTasbeeha(View view) {
        String catId = view.getTag().toString();

        int ii=Integer.valueOf(tasabeehCursor.getInt(tasabeehCursor.getColumnIndex("CAT_IS_USER_DEFINED")));
        if(ii==1){
            DB_Adapter.open();
            DB_Adapter.delete_Tasbeeh_Categories(Integer.parseInt(catId));
            DB_Adapter.close();
            this.gvTasabeh.setAdapter(new LogItemCursor(this, DB_Adapter.selectAll_Tasabeeh_CategoriesT()));//(), type.TASABEEH));
            Utils.getToast(this, getString(R.string.succesfullyDeleted), R.drawable.success, 0).show();
        }
    }
    private void insertTasbeha(String tasbehaTxt) {
        DB_Adapter.open();
        DB_Adapter.insert_Tasbeeh_Categories(tasbehaTxt.replace("\"", XmlPullParser.NO_NAMESPACE).replace("'", "â€˜").replace("--", " "), "0", "doa", 0, 1);
        DB_Adapter.close();
        this.gvTasabeh.setAdapter(new LogItemCursor(this, DB_Adapter.selectAll_Tasabeeh_CategoriesT()));
    }
    
}
