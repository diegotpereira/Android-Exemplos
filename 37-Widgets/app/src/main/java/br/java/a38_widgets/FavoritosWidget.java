package br.java.a38_widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class FavoritosWidget extends AppWidgetProvider {

    public static final String EXTRA_ACAO    = "acao";
    public static final String ACAO_ANTERIOR = "anterior";
    public static final String ACAO_PROXIMO  = "proximo";
    public static final String ACAO_EXCLUIR  = "excluir";
    public static final String ACAO_SITE     = "site";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        RemoteViews views = new RemoteViews(
                context.getPackageName(), R.layout.widget_favoritos);

        // There may be multiple widgets active, so update all of them
        for (int i = 0; i < appWidgetIds.length; i++) {
            configuraClickBotoes(context, appWidgetIds[i], views);
        }

        appWidgetManager.updateAppWidget(appWidgetIds, views);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private void configuraClickBotoes(Context context, int appWidgetId, RemoteViews views) {
        views.setOnClickPendingIntent(R.id.btnProximo,
                servicePendingIntent(context, ACAO_PROXIMO, appWidgetId));
        views.setOnClickPendingIntent(R.id.btnAnterior,
                servicePendingIntent(context, ACAO_ANTERIOR, appWidgetId));
        views.setOnClickPendingIntent(R.id.txtSite,
                servicePendingIntent(context, ACAO_SITE, appWidgetId));
    }

    private PendingIntent servicePendingIntent(
            Context ctx, String acao, int appWidgetId) {
        Intent serviceIntent = new Intent(acao);
        serviceIntent.putExtra(EXTRA_ACAO, acao);
        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent pit = PendingIntent.getService(
                ctx, appWidgetId, serviceIntent, 0);

        return pit;
    }


}