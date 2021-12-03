package br.java.a27_notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;
import androidx.core.app.TaskStackBuilder;

public class NotificationUtils {

    public static final String ACAO_DELETE =
            "br.java.a27_notification.DELETE_NOTIFICACAO";

    public static final String ACAO_NOTIFICACAO =
            "br.java.a27_notification.ACAO_NOTIFICACAO";

    private static PendingIntent criarPendingIntent(
            Context ctx, String texto, int id) {

        Intent resultIntent = new Intent(ctx, DetalheActivity.class);
        resultIntent.putExtra(DetalheActivity.EXTRA_TEXTO, texto);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(ctx);
        stackBuilder.addParentStack(DetalheActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        return stackBuilder.getPendingIntent(id, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static void criarNotificacaoSimples(Context ctx, String texto, int id) {

        PendingIntent resultPendingIntent = criarPendingIntent(ctx, texto, id);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(ctx)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setSmallIcon(R.drawable.ic_notificacao)
                    .setContentTitle("Simples " + id)
                    .setContentText(texto)
                    .setContentIntent(resultPendingIntent);

        NotificationManagerCompat nm = NotificationManagerCompat.from(ctx);
        nm.notify(id, mBuilder.build());

    }
    public static void criarNotificacaoCompleta(Context ctx, String texto, int id) {

        Uri uriSom = Uri.parse("android.resource://" +
                ctx.getPackageName() + "/raw/som_notificacao");

        PendingIntent pitAcao = PendingIntent.getBroadcast(
                ctx, 0, new Intent(ACAO_NOTIFICACAO), 0);

        PendingIntent pitDelete = PendingIntent.getBroadcast(
                ctx, 0, new Intent(ACAO_DELETE), 0);

        Bitmap largeIcon = BitmapFactory.decodeResource(
                ctx.getResources(), R.mipmap.ic_launcher);

        PendingIntent pitNotificacao = criarPendingIntent(ctx, texto, id);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(ctx)
                        .setSmallIcon(R.drawable.ic_notificacao)
                        .setColor(Color.RED)
                        .setContentTitle("Completa")
                        .setContentText(texto)
                        .setTicker("Chegou uma notificação")
                        .setWhen(System.currentTimeMillis())
                        .setLargeIcon(largeIcon)
                        .setAutoCancel(true)
                        .setContentIntent(pitNotificacao)
                        .setDeleteIntent(pitDelete)
                        .setLights(Color.BLUE, 1000, 5000)
                        .setSound(uriSom)
                        .setVibrate(new long[]{100, 500, 200, 800})
                        .addAction(R.drawable.ic_acao_notificacao, "Ação Customizada", pitAcao)
                        .setNumber(id)
                        .setFullScreenIntent(pitNotificacao, false)
                        .setSubText("Subtexto");

        NotificationManagerCompat nm = NotificationManagerCompat.from(ctx);
        nm.notify(id, mBuilder.build());

    }
    public static void criarNotificacaoBig(Context ctx, int idNotificacao) {

        int numero = 5;
        NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("Mensagens não lidas: ");

        for(int i = 1; i <= numero; i++) {
            inboxStyle.addLine("Mensagem " + i);
        }

        inboxStyle.setSummaryText("Clique para exibir");

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ctx)
                .setSmallIcon(R.drawable.ic_notificacao)
                .setColor(Color.RED)
                .setContentTitle("Notificação")
                .setContentText("Vários itens pendentes")
                .setContentIntent(criarPendingIntent(ctx, "Mensagens não lidas", -1))
                .setNumber(numero)
                .setStyle(inboxStyle);

        NotificationManagerCompat nm = NotificationManagerCompat.from(ctx);
        nm.notify(idNotificacao, mBuilder.build());



    }
    public static void criarNotificacaoComResposta(Context ctx, int idNotificacao) {

        RemoteInput remoteInput = new RemoteInput.Builder(DetalheActivity.EXTRA_RESPOSTA_VOZ)
                .setLabel("Diga a resposta")
                .build();

        PendingIntent pit = criarPendingIntent(ctx, "Notificação com resposta", idNotificacao);
        NotificationCompat.Action action = new NotificationCompat.Action.Builder(
                R.drawable.ic_acao_notificacao, "Responder", pit)
                .addRemoteInput(remoteInput)
                .build();

        NotificationCompat.WearableExtender wearableExtender =
                new NotificationCompat.WearableExtender();
        Notification notificacao = new NotificationCompat.Builder(ctx)
                .setSmallIcon(R.drawable.ic_notificacao)
                .setContentTitle("Com resposta")
                .setContentText("Passe a página para responder")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .extend(wearableExtender.addAction(action))
                .build();

        NotificationManagerCompat nm = NotificationManagerCompat.from(ctx);
        nm.notify(idNotificacao, notificacao);


    }
    public static void criarNotificacaoComPaginas(Context ctx, String texto, int id) {

    }
    public static void criarNotificacaoAgrupadas(Context ctx, String texto, int id) {

    }
}
