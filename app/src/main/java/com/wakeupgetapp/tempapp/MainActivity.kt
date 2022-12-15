package com.wakeupgetapp.tempapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wakeupgetapp.tempapp.ui.theme.TempAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TempAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TempAppTheme {
        Greeting("Android")
    }
}

@Composable
fun ChooseLoginMethodView(navController: NavController, sharedViewModel: SharedViewModel) {
    val scrollState = remember { ScrollState(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .fillMaxHeight()
            .verticalScroll(scrollState)

    ) {
        Column(
            Modifier
                .padding(top = 160.dp)
                .padding(horizontal = 24.dp)
        ) {
            Text(stringResource(id = R.string.welcome_to), style = h4)
            Text(stringResource(id = R.string.welcome_app_name), style = h1, color = MaterialTheme.colors.primary)

        }
        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 68.dp)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        )
        {
            Text(stringResource(id = R.string.register_new_account), modifier = Modifier.padding(bottom = 6.dp))
            IconTextButton(
                navController,
                stringResource(R.string.continue_with_Google),
                "google",
                "SignUpGoogleScreen"
            )
            IconTextButton(
                navController,
                stringResource(R.string.continue_with_email),
                "alternate_email",
                "SignUpEmailScreen"
            )
            Row(
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(stringResource(id = R.string.already_have_account), modifier = Modifier.padding(horizontal = 8.dp))
                Text(
                    stringResource(id = R.string.log_in),
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.primary,
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clickable { navController.navigate("LoginScreen") },
                )
            }
        }

    }
}

@Composable
fun IconTextButton(navController: NavController, text: String, iconName: String, rout: String) {
    var iconId = -1
    if (!iconName.equals("")) {
        val context = LocalContext.current
        iconId = remember(iconName) {
            context.resources.getIdentifier(
                iconName,
                "drawable",
                context.packageName
            )
        }
    }

    Button(
        onClick = { navController.navigate(rout) },
        border = BorderStroke(1.5.dp, Color.Black),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        if (!iconId.equals(-1)) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = iconName,
                tint = Color.Unspecified
            )
        }
        Spacer(
            modifier = Modifier
                .width(width = 8.dp)
        )
        Text(
            text = text,
            color = Color(0xff070707),
            textAlign = TextAlign.Center,
            lineHeight = 24.sp,
            style = body_16
        )
    }
}

@Composable
fun SignUpEmailView(navController: NavController, sharedViewModel: SharedViewModel) {
    val context = LocalContext.current

    val scrollState = remember { ScrollState(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)
            .verticalScroll(scrollState)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .width(48.dp)
                .height(48.dp)
                .clickable { navController.navigateUp() }
        )

        Column(
            modifier = Modifier
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
            ) {
                Text(text = stringResource(id = R.string.register), color = MaterialTheme.colors.primary, style = h1)
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(top = 5.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                InputFieldLabelIcon(
                    text = stringResource(id = R.string.your_email_address),
                    onValueChange = {},
                    fieldLabel = "Email",
                    iconName = "alternate_email",
                    viewModelVariable = sharedViewModel.email
                )
                InputFieldLabelIcon(
                    text = stringResource(id = R.string.your_password),
                    onValueChange = {},
                    fieldLabel = stringResource(id = R.string.password),
                    iconName = "lock",
                    viewModelVariable = sharedViewModel.password,
                    isPassword = true
                )
                InputFieldLabelIcon(
                    text = stringResource(id = R.string.type_your_first_name),
                    onValueChange = {},
                    fieldLabel = stringResource(id = R.string.first_name),
                    iconName = "",
                    viewModelVariable = sharedViewModel.firstName
                )
                InputFieldLabelIcon(
                    text = stringResource(id = R.string.type_your_last_name),
                    onValueChange = {},
                    fieldLabel = stringResource(id = R.string.last_name),
                    iconName = "",
                    viewModelVariable = sharedViewModel.lastName
                )
            }

            ChooseRoleSection(sharedViewModel)

            Column(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .padding(horizontal = 12.dp)
            ) {
                RegisterButton(navController, stringResource(id = R.string.sign_in), sharedViewModel, context)

                Row(
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = stringResource(id = R.string.already_have_account), modifier = Modifier.padding(horizontal = 8.dp))
                    Text(
                        text = stringResource(id = R.string.log_in),
                        fontSize = 16.sp,
                        color = MaterialTheme.colors.primary,
                        style = TextStyle(textDecoration = TextDecoration.Underline),
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clickable { navController.navigate("LoginScreen") }
                    )
                }
            }
        }
    }
}

@Composable
fun RegisterButton(
    navController: NavController,
    text: String,
    sharedViewModel: SharedViewModel,
    context: Context
) {
    Button(
        onClick = {
            if (sharedViewModel.isFunctionCarer.value) {
                sharedViewModel.function.value = "Carer"
            }
            if (sharedViewModel.isFunctionSenior.value) {
                sharedViewModel.function.value = "Senior"
            }

            if (sharedViewModel.function.value == "") {
                Toast.makeText(context, context.getString(R.string.you_must_choose_function), Toast.LENGTH_LONG).show()
            } else {
                sharedViewModel.registerUser(
                    sharedViewModel,
                    sharedViewModel.email.value,
                    sharedViewModel.password.value,
                    sharedViewModel.firstName.value,
                    sharedViewModel.lastName.value,
                    sharedViewModel.function.value
                )
                navController.navigate("LoadingRegisterView")
            }
        },
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff7929e8)),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}


@Composable
fun InputFieldLabelIcon(
    text: String,
    onValueChange: (String) -> Unit,
    fieldLabel: String,
    iconName: String,
    viewModelVariable: MutableState<String>,
    isPassword: Boolean = false
) {
    var iconId = -1

    if (iconName != "") {
        val context = LocalContext.current
        iconId = remember(iconName) {
            context.resources.getIdentifier(
                iconName,
                "drawable",
                context.packageName
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text(text = fieldLabel)
            if (iconId > -1) {
                Icon(
                    painter = painterResource(id = iconId),
                    contentDescription = iconName,
                    tint = Color.Black,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }

        if (!isPassword) {
            InputField(text, onValueChange, viewModelVariable)

        } else {
            PasswordInputField(text, onValueChange, viewModelVariable)
        }

    }
}

@Composable
fun InputField(
    placeholder: String,
    onValueChange: (String) -> Unit,
    viewModelVariable: MutableState<String>
) {
    val text = viewModelVariable.value

    OutlinedTextField(
        value = text,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            backgroundColor = Color.White,
            disabledIndicatorColor = MaterialTheme.colors.primary,
            unfocusedIndicatorColor = MaterialTheme.colors.primary,
            focusedIndicatorColor = MaterialTheme.colors.primary,
        ),
        shape = RoundedCornerShape(20.dp),
        onValueChange = { newText -> viewModelVariable.value = newText },
        placeholder = { Text(placeholder) },
        textStyle = TextStyle(
            fontSize = 14.sp
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun PasswordInputField(
    placeholder: String,
    onValueChange: (String) -> Unit,
    viewModelVariable: MutableState<String>
) {
    val text = viewModelVariable.value
    var passwordVisibility: Boolean by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = text,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            backgroundColor = Color.White,
            disabledIndicatorColor = MaterialTheme.colors.primary,
            unfocusedIndicatorColor = MaterialTheme.colors.primary,
            focusedIndicatorColor = MaterialTheme.colors.primary,
        ),
        shape = RoundedCornerShape(20.dp),
        onValueChange = { newText -> viewModelVariable.value = newText },
        placeholder = { Text(placeholder) },
        textStyle = TextStyle(
            fontSize = 14.sp
        ),
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisibility)
                Icons.Default.Visibility
            else Icons.Default.VisibilityOff

            IconButton(onClick = {
                passwordVisibility = !passwordVisibility
            }) {
                Icon(
                    imageVector = image, contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp)
                )

                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(imageVector = image, null)
                }
            }
        }
    )
}


@Composable
fun SignUpGoogleView(navController: NavController, sharedViewModel: SharedViewModel) {
    val context = LocalContext.current
    val scrollState = remember { ScrollState(0) }

    // GOOGLE
    val state by sharedViewModel.loadingGoogleSignInState.collectAsState()
    // Equivalent of onActivityResult
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
            sharedViewModel.signWithCredential(credential)
            sharedViewModel.userDataFromGoogle(account.email!!, account.displayName!!)
        } catch (e: ApiException) {
            Log.w("TAG", "Google sign in failed", e)
        }
    }
    //

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)
            .verticalScroll(scrollState)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .width(48.dp)
                .height(48.dp)
                .clickable { navController.navigateUp() }
        )

        Column(
            modifier = Modifier
                .padding(top = 50.dp)
                .padding(horizontal = 24.dp)
        ) {
            Text(text = stringResource(id = R.string.register), color = MaterialTheme.colors.primary, style = h1)
            Text(text = stringResource(id = R.string.choose_function))
        }

        ChooseRoleSection(sharedViewModel)

        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
                .padding(horizontal = 12.dp)
        ) {
            GoogleSignInButton(
                navController,
                stringResource(R.string.continue_with_Google),
                "google",
                "",
                sharedViewModel,
                launcher
            )

            Row(
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = stringResource(id = R.string.already_have_account), modifier = Modifier.padding(horizontal = 8.dp))
                Text(
                    text = stringResource(id = R.string.log_in),
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.primary,
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clickable { navController.navigate("LoginScreen") }
                )
            }
        }
    }

    when(state.status) {
        LoadingState.Status.SUCCESS -> {
            LaunchedEffect(""){
                if (sharedViewModel.isNewUser.value == true){
                    sharedViewModel.writeNewUserFromGoogle(sharedViewModel.userData)
                }
                sharedViewModel.getUserData()
                navController.navigate("LoadingDataView")
            }
        }
        LoadingState.Status.FAILED -> {
            Toast.makeText(context, context.getString(R.string.error_with_signing_in), Toast.LENGTH_LONG).show()
        }
        else -> {}
    }
}

@Composable
fun GoogleSignInButton(navController: NavController, text: String, iconName: String, rout: String, sharedViewModel: SharedViewModel, launcher: ManagedActivityResultLauncher<Intent, ActivityResult>) {
    var iconId = -1
    if (!iconName.equals("")) {
        val context = LocalContext.current
        iconId = remember(iconName) {
            context.resources.getIdentifier(
                iconName,
                "drawable",
                context.packageName
            )
        }
    }

    val context = LocalContext.current
    val token = stringResource(R.string.webclient_id)

    Button(
        onClick = {
            if (sharedViewModel.isFunctionCarer.value){
                sharedViewModel.function.value = "Carer"
            }
            if (sharedViewModel.isFunctionSenior.value){
                sharedViewModel.function.value = "Senior"
            }

            if (sharedViewModel.function.value == "") {
                Toast.makeText(context, context.getString(R.string.you_must_choose_function), Toast.LENGTH_LONG).show()
            }
            else{
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(token)
                    .requestEmail()
                    .build()

                val googleSignInClient = GoogleSignIn.getClient(context, gso)
                launcher.launch(googleSignInClient.signInIntent)
            }
        },
        border = BorderStroke(1.5.dp, Color.Black),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        if (!iconId.equals(-1)) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = iconName,
                tint = Color.Unspecified
            )
        }
        Spacer(
            modifier = Modifier
                .width(width = 8.dp)
        )
        Text(
            text = text,
            color = Color(0xff070707),
            textAlign = TextAlign.Center,
            lineHeight = 24.sp,
            style = body_16
        )
    }
}

data class LoadingState private constructor(val status: Status, val msg: String? = null) {
    companion object {
        val LOADED = LoadingState(Status.SUCCESS)
        val IDLE = LoadingState(Status.IDLE)
        val LOADING = LoadingState(Status.RUNNING)
        fun error(msg: String?) = LoadingState(Status.FAILED, msg)
    }

    enum class Status {
        RUNNING,
        SUCCESS,
        FAILED,
        IDLE,
    }
}