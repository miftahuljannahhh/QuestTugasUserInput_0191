package com.example.tugas

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Composable
private fun dimenSp(id: Int): TextUnit {
    val dp = dimensionResource(id)
    return with(LocalDensity.current) { dp.toSp() }
}

@Suppress("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BiodataScreen() {
    val cardShape = RoundedCornerShape(
        topStart = dimensionResource(R.dimen.card_corner),
        topEnd = dimensionResource(R.dimen.card_corner)
    )

    Box(modifier = Modifier.fillMaxSize()) {

        Scaffold(containerColor = colorResource(R.color.screen_background)) { _ ->
            Box {
                Image(
                    painter = painterResource(R.drawable.bg_header),
                    contentDescription = stringResource(R.string.cd_background),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.TopCenter,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(R.dimen.header_image_height))
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(R.dimen.header_image_height))
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    colorResource(R.color.transparent),
                                    colorResource(R.color.header_overlay)
                                )
                            )
                        )
                )
            }
        }

        Box(
            modifier = Modifier
                .padding(top = dimensionResource(R.dimen.card_top_margin))
                .fillMaxSize()
                .shadow(dimensionResource(R.dimen.card_elevation), cardShape)
                .background(colorResource(R.color.white), cardShape)
        ) {
            BiodataForm()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BiodataForm() {
    val context = LocalContext.current
    val experiences = stringArrayResource(R.array.experience_options)
    val skillNames = listOf(
        stringResource(R.string.skill_android),
        stringResource(R.string.skill_uiux),
        stringResource(R.string.skill_web)
    )
    val separator = stringResource(R.string.skill_separator)
    val datePattern = stringResource(R.string.date_pattern)
    val timeZoneId = stringResource(R.string.date_timezone)

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    val skills = remember {
        mutableStateListOf<Boolean>().apply { repeat(skillNames.size) { add(false) } }
    }

    var showDatePicker by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    val isValid = firstName.isNotBlank() && lastName.isNotBlank() && email.isNotBlank() &&
            birthDate.isNotBlank() && experience.isNotBlank() && skills.any { it }

    val fieldShape = RoundedCornerShape(dimensionResource(R.dimen.field_corner))
    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = colorResource(R.color.purple_title),
        unfocusedBorderColor = colorResource(R.color.field_border),
        focusedContainerColor = colorResource(R.color.field_container),
        unfocusedContainerColor = colorResource(R.color.field_container),
        focusedTextColor = colorResource(R.color.field_text),
        unfocusedTextColor = colorResource(R.color.field_text),
        cursorColor = colorResource(R.color.purple_title)
    )
    val fieldTextStyle = TextStyle(fontSize = dimenSp(R.dimen.text_field))
    val fieldModifier = Modifier.heightIn(min = dimensionResource(R.dimen.field_height))
    val noRipple = remember { MutableInteractionSource() }

    @Composable
    fun Hint(textRes: Int) = Text(
        text = stringResource(textRes),
        fontSize = dimenSp(R.dimen.text_field),
        color = colorResource(R.color.hint_text)
    )

