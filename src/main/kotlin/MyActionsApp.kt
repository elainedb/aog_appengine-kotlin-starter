import com.google.actions.api.ActionRequest
import com.google.actions.api.ActionResponse
import com.google.actions.api.DialogflowApp
import com.google.actions.api.ForIntent
import extensions.getFormattedString
import org.slf4j.LoggerFactory
import java.util.*

/**
 * Implements all intent handlers for this Action. Note that your App must extend from DialogflowApp
 * if using Dialogflow or ActionsSdkApp for ActionsSDK based Actions.
 */
class MyActionsApp : DialogflowApp() {

    private val LOGGER = LoggerFactory.getLogger(MyActionsApp::class.java)

    @ForIntent("Default Welcome Intent")
    fun welcome(request: ActionRequest): ActionResponse {
        LOGGER.info("Welcome intent start.")

        val locale = Locale(request.locale.language)
        val responseBuilder = getResponseBuilder(request)

        if (request.user?.lastSeen != null) {
            responseBuilder.add(getStringResource("welcome_back", locale))
        } else {
            responseBuilder.add(getStringResource("welcome", locale))
        }

        LOGGER.info("Welcome intent end.")
        return responseBuilder.build()
    }

    @ForIntent("bye")
    fun bye(request: ActionRequest): ActionResponse {
        LOGGER.info("Bye intent start.")

        val locale = Locale(request.locale.language)
        val responseBuilder = getResponseBuilder(request)

        responseBuilder.add(getStringResource("bye", locale)).endConversation()
        LOGGER.info("Bye intent end.")
        return responseBuilder.build()
    }

    private fun getStringResource(key: String, locale: Locale): String {
        return ResourceBundle.getBundle("resources", locale).getFormattedString(key)
    }
}