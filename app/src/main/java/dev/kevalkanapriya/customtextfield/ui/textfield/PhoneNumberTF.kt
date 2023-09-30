package dev.kevalkanapriya.customtextfield.ui.textfield

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kevalkanapriya.customtextfield.ui.textfield.util.checkPhoneNumber
import dev.kevalkanapriya.customtextfield.ui.textfield.util.getDefaultLangCode
import dev.kevalkanapriya.customtextfield.ui.textfield.util.getDefaultPhoneCode
import dev.kevalkanapriya.customtextfield.ui.textfield.util.getNumberHint
import dev.kevalkanapriya.customtextfield.util.PhoneNumberTransformation

private var fullNumberState: String by mutableStateOf("")
private var checkNumberState: Boolean by mutableStateOf(false)
private var phoneNumberState: String by mutableStateOf("")
private var countryCodeState: String by mutableStateOf("")

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Preview
@Composable
fun PhoneNumberTF(
//    modifier: Modifier = Modifier,
//    text: String,
//    onValueChange: (String) -> Unit,
//    shape: Shape = RoundedCornerShape(24.dp),
//    color: Color = MaterialTheme.colorScheme.background,
//    showCountryCode: Boolean = true,
//    showCountryFlag: Boolean = true,
//    focusedBorderColor: Color = MaterialTheme.colorScheme.primary,
//    unfocusedBorderColor: Color = MaterialTheme.colorScheme.onSecondary,
//    cursorColor: Color = MaterialTheme.colorScheme.primary,
//    bottomStyle: Boolean = false
) {

    val context = LocalContext.current
    var phoneNo by rememberSaveable {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val maxChar = 10

    var phoneCode by rememberSaveable {
        mutableStateOf(
            getDefaultPhoneCode(
                context
            )
        )
    }
    var defaultLang by rememberSaveable {
        mutableStateOf(
            getDefaultLangCode(context)
        )
    }
    val interactionSource = remember { MutableInteractionSource() }

    fullNumberState = phoneCode + phoneNo
    phoneNumberState = phoneNo
    countryCodeState = defaultLang

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {


        BasicTextField(
            value = phoneNo,
            textStyle = TextStyle(
                fontSize = 20.sp
            ),
            onValueChange = {
                phoneNo = if (it.length <= maxChar) {
                    it
                } else {
                    it.take(maxChar)
                }
                            },
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(0.dp)
            ,
            cursorBrush = Brush.verticalGradient(
                0.00f to Color.Transparent,
                0.10f to Color.Transparent,
                0.10f to Color.Green,
                0.90f to Color.Green,
                0.90f to Color.Transparent,
                1.00f to Color.Transparent
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.NumberPassword,
                autoCorrect = true,
            ),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            }),
            singleLine = true,
            interactionSource = interactionSource,
            visualTransformation = PhoneNumberTransformation(getLibCountries.single { it.countryCode == defaultLang }.countryCode.uppercase())
        ) { innerTextField ->

            TextFieldDefaults.TextFieldDecorationBox(
                value = phoneNo,
                innerTextField = innerTextField,
                enabled = true,
                singleLine = true,
                visualTransformation = PhoneNumberTransformation(getLibCountries.single { it.countryCode == defaultLang }.countryCode.uppercase()),
                interactionSource = interactionSource,
                placeholder = { Text(text = stringResource(id = getNumberHint(getLibCountries.single { it.countryCode == defaultLang }.countryCode.lowercase()))) },
                leadingIcon = {
                    TogiCodeDialog(
                        modifier = Modifier.offset(x = -1.dp),
                        pickedCountry = {
                            phoneCode = it.countryPhoneCode
                            defaultLang = it.countryCode
                        },
                        defaultSelectedCountry = getLibCountries.single { it.countryCode == defaultLang },
                        showCountryCode = false,
                        showFlag = true
                    )
                },
                contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                    top = 0.dp,
                    bottom = 0.dp,
                    start = 20.dp,
                    end = 0.dp
                ),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Green,
                    unfocusedIndicatorColor = Color.Green,
                    cursorColor = Color.Green,
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                container = {

                    CustomFilledContainerBox(
                        false,
                        false,
                        interactionSource,
                        MaterialTheme.colorScheme.surface,
                        Color.Green
                    )

                },
            )
        }

    }
}

fun getFullPhoneNumber(): String {
    return fullNumberState
}

fun getOnlyPhoneNumber(): String {
    return phoneNumberState
}

fun getErrorStatus(): Boolean {
    return !checkNumberState
}

fun isPhoneNumber(): Boolean {
    val check = checkPhoneNumber(
        phone = phoneNumberState, fullPhoneNumber = fullNumberState, countryCode = countryCodeState
    )
    checkNumberState = check
    return check
}