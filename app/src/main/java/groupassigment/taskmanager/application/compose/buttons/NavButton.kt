package groupassigment.taskmanager.application.compose.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun NavButton(icon: ImageVector, text: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth(),
        onClick = onClick,
        contentPadding = PaddingValues(8.dp),
        colors = ButtonDefaults.buttonColors(Color.Gray)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(imageVector = icon, contentDescription = null)
            Text(text = text)
        }
    }
}