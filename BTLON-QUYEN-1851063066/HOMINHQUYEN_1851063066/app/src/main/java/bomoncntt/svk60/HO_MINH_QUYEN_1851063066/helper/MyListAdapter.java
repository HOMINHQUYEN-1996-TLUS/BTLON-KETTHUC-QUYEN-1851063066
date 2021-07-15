package bomoncntt.svk60.HO_MINH_QUYEN_1851063066.helper;
import android.app.Activity;

import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import bomoncntt.svk60.HO_MINH_QUYEN_1851063066.R;
import bomoncntt.svk60.HO_MINH_QUYEN_1851063066.model.SinhVien;

public class MyListAdapter extends ArrayAdapter<SinhVien> {

    private final Activity context;
    private final ArrayList<SinhVien> listsv;

    private static class ViewHolder {
        TextView txtMasv;
        TextView txtTensv;
        TextView txtgt;
        TextView txtlop;
        ImageView imageView;
    }
    //ViewHolder.txtMasv="001"
    public MyListAdapter(Activity context, ArrayList<SinhVien> data) {
        super(context, R.layout.layout_item_sv, data);
        // TODO Auto-generated constructor stub
        this.context=context;
        this.listsv = data;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        SinhVien dataModel = getItem(position); //lấy object (masv,tensv,gt,lop)
        ViewHolder viewHolder; //cục bộ
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(R.layout.layout_item_sv, parent, false);

            viewHolder.txtMasv =  convertView.findViewById(R.id.item_txtmasv);
            viewHolder.txtTensv =  convertView.findViewById(R.id.item_txttensv);
            viewHolder.txtgt=convertView.findViewById(R.id.item_txtgt);
            viewHolder.txtlop=convertView.findViewById(R.id.item_txtlop);
            viewHolder.imageView=convertView.findViewById(R.id.avartar);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtMasv.setText(dataModel.getMasv());
        viewHolder.txtTensv.setText(dataModel.getTensv());
        viewHolder.txtgt.setText(dataModel.getGt());
        viewHolder.txtlop.setText(dataModel.getLop());
        String anhsv = dataModel.getHinhanh();
        viewHolder.imageView.setImageBitmap(AllConTrol.StringToBitMap(anhsv));
        return convertView;

    };
}