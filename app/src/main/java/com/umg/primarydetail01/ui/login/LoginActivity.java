package com.umg.primarydetail01.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.umg.primarydetail01.ItemDetailHostActivity;
import com.umg.primarydetail01.R;
import com.umg.primarydetail01.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "USER_EMAIL_ON_MESSAGE";
    public static String user_email = "";

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        final ProgressBar loadingProgressBar = binding.loading;

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);

                user_email = usernameEditText.getText().toString();

                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());

                justAfterLogin();

            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void justAfterLogin() {
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder().serializeNulls().create()
                ))
                .build();
        PokemonAPIService pokemonApiService = retrofit.create(PokemonAPIService.class);
        Call<PokemonFetchResults> call = pokemonApiService.getPokemons();

        call.enqueue(new Callback<PokemonFetchResults>() {
            //@RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<PokemonFetchResults> call,
                                   Response<PokemonFetchResults> response) {
                if (response.isSuccessful()) {
                    ArrayList pokemonList = response.body().getResults();

                    for(int i = 1; i <= 10; i++)
                    {
                        //Pokemon pokemon = (Pokemon)pokemonList.get(i);

                        Object getrow = pokemonList.get(i);
                        LinkedTreeMap<Object,Object> t = (LinkedTreeMap) getrow;
                        String name = t.get("name").toString();

                        System.out.println(name);

                        //trying to create on item
                        PlaceholderContent.startOneItem(i, name, "It's a " + name + "!");

                    }

                   // View recyclerView = findViewById(R.id.item_list);
                    //assert recyclerView != null;
                    //setupRecyclerView((RecyclerView) recyclerView, pokemonList);
                } else {
                    Log.d("Error", "Something happened");
                    return;
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("Error", t.toString());
            }
        });*/



        Intent intent = new Intent(this, ItemDetailHostActivity.class);
        //EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
        //String message = editText.getText().toString();
        String message = "Start the Item Detail Host Activity";
        intent.putExtra(EXTRA_MESSAGE, user_email);
        startActivity(intent);
    }
}