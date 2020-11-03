package com.realstate.bdland.Adapter.RecyclerViewAdapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.realstate.bdland.Database.Data_model.ClientDataRequestModel;
import com.realstate.bdland.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientRecyclerAdapter extends RecyclerView.Adapter<ClientRecyclerAdapter.ViewHolder> {

    public static final String TAG = "TAG";
    private FirebaseFirestore firebaseFirestore;
    private DocumentReference documentReference;


    private ArrayList<ClientDataRequestModel> clientDataRequestModelArrayList = new ArrayList<>();
    private Context mContext;

    public ClientRecyclerAdapter(ArrayList<ClientDataRequestModel> clientDataRequestModelArrayList, Context mContext) {
        this.clientDataRequestModelArrayList = clientDataRequestModelArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: is called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carddesign_clientrequestment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        Log.d(TAG, "onBindViewHolder: is called");

        holder.Client_Name(clientDataRequestModelArrayList.get(position).getCLIENT_NAME());
        holder.Client_PhoneNumber(clientDataRequestModelArrayList.get(position).getCLIENT_PHONE_NUMBER());
        holder.Client_Requirement(clientDataRequestModelArrayList.get(position).getCLIENT_REQUIREMENT());
        holder.Client_requested_rent(clientDataRequestModelArrayList.get(position).getCLIENT_REQUEST_RENT());
        holder.Client_Occupation(clientDataRequestModelArrayList.get(position).getCLIENT_OCCUPATION());
        holder.ClientFamilyMember(clientDataRequestModelArrayList.get(position).getCLIENT_FAMILY_MEMBER());
        holder.Client_request_sector(clientDataRequestModelArrayList.get(position).getCLIENT_REQUESTED_SECTOR());

        String status = clientDataRequestModelArrayList.get(position).getREQUEST_STATUS();

        if (status.equals("NO")) {
            holder.statusPending(true);
        } else {
            holder.statuscomplete(true);
        }

        holder.StatusPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        firebaseFirestore = FirebaseFirestore.getInstance();

                        String id = clientDataRequestModelArrayList.get(position).getCLIENT_ID();
                        Log.d(TAG, "onClick: id == > " + id);

                        documentReference = firebaseFirestore.collection("CLIENT REQUEST").document(id);
                        Map<String, Object> Client = new HashMap<>();
                        Client.put("CLIENT_NAME", clientDataRequestModelArrayList.get(position).getCLIENT_NAME());
                        Client.put("CLIENT_PHONE_NUMBER", clientDataRequestModelArrayList.get(position).getCLIENT_PHONE_NUMBER());
                        Client.put("CLIENT_REQUIREMENT", clientDataRequestModelArrayList.get(position).getCLIENT_REQUIREMENT());
                        Client.put("CLIENT_REQUEST_RENT", clientDataRequestModelArrayList.get(position).getCLIENT_REQUEST_RENT());
                        Client.put("CLIENT_OCCUPATION", clientDataRequestModelArrayList.get(position).getCLIENT_OCCUPATION());
                        Client.put("CLIENT_FAMILY_MEMBER", clientDataRequestModelArrayList.get(position).getCLIENT_FAMILY_MEMBER());
                        Client.put("CLIENT_REQUESTED_SECTOR", clientDataRequestModelArrayList.get(position).getCLIENT_REQUESTED_SECTOR());
                        Client.put("CLIENT_ID", id);
                        Client.put("REQUEST_STATUS", "YES");

                        documentReference.set(Client).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                holder.statuscomplete(true);
                                ClientRecyclerAdapter.this.notifyItemChanged(position);
                                Log.d(TAG, "onSuccess: is called from database");
                                Toast.makeText(mContext, "Status Updated", Toast.LENGTH_SHORT).show();
                                clientDataRequestModelArrayList.remove(clientDataRequestModelArrayList.size()-1);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, clientDataRequestModelArrayList.size());

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(mContext, "Status updating Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onFailure: is called from data base " + e.getMessage());

                            }
                        });

                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();




            }
        });

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog nagDialog = new Dialog(mContext);
                nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                nagDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                nagDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                nagDialog.setCancelable(false);
                nagDialog.setCanceledOnTouchOutside(true);
                nagDialog.setContentView(R.layout.carddesignclienteditpopup);
                final EditText Name, Number, clientRequirement, Rent, occupation, familyMember, requireSectorOptional;
                Name = nagDialog.findViewById(R.id.name_clients);
                Number = nagDialog.findViewById(R.id.Phone_clients);
                clientRequirement = nagDialog.findViewById(R.id.client_Requirements);
                Rent = nagDialog.findViewById(R.id.client_rents);
                occupation = nagDialog.findViewById(R.id.clinet_Occupation_detailss);
                familyMember = nagDialog.findViewById(R.id.client_familyMembers);
                requireSectorOptional = nagDialog.findViewById(R.id.client_require_sectors);

                Name.setText(clientDataRequestModelArrayList.get(position).getCLIENT_NAME());
                Number.setText(clientDataRequestModelArrayList.get(position).getCLIENT_PHONE_NUMBER());
                clientRequirement.setText(clientDataRequestModelArrayList.get(position).getCLIENT_REQUIREMENT());
                Rent.setText(clientDataRequestModelArrayList.get(position).getCLIENT_REQUEST_RENT());
                occupation.setText(clientDataRequestModelArrayList.get(position).getCLIENT_OCCUPATION());
                familyMember.setText(clientDataRequestModelArrayList.get(position).getCLIENT_FAMILY_MEMBER());
                requireSectorOptional.setText(clientDataRequestModelArrayList.get(position).getCLIENT_REQUESTED_SECTOR());

                nagDialog.findViewById(R.id.clintRequestUploads).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Name.getText().toString().isEmpty() || Number.getText().toString().isEmpty() || clientRequirement.getText().toString().isEmpty() || Rent.getText().toString().isEmpty() ||
                                occupation.getText().toString().isEmpty() || familyMember.getText().toString().isEmpty()) {
                            Toast.makeText(mContext, "One of the Field is Empty", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                        String id = clientDataRequestModelArrayList.get(position).getCLIENT_ID();
                        DocumentReference documentReference = firebaseFirestore.collection("CLIENT REQUEST").document(id);

                        Map<String, Object> Client = new HashMap<>();
                        Client.put("CLIENT_NAME", Name.getText().toString());
                        Client.put("CLIENT_PHONE_NUMBER", Number.getText().toString());
                        Client.put("CLIENT_REQUIREMENT", clientRequirement.getText().toString());
                        Client.put("CLIENT_REQUEST_RENT", Rent.getText().toString());
                        Client.put("CLIENT_OCCUPATION", occupation.getText().toString());
                        Client.put("CLIENT_FAMILY_MEMBER", familyMember.getText().toString());
                        Client.put("CLIENT_REQUESTED_SECTOR", requireSectorOptional.getText().toString());
                        Client.put("REQUEST_STATUS", "NO");
                        Client.put("CLIENT_ID", id);

                        documentReference.set(Client).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                              ClientRecyclerAdapter.this.notifyItemChanged(position);
                                clientDataRequestModelArrayList.remove(clientDataRequestModelArrayList.size()-1);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, clientDataRequestModelArrayList.size());

                                Name.setText("");
                                Number.setText("");
                                clientRequirement.setText("");
                                Rent.setText("");
                                occupation.setText("");
                                familyMember.setText("");
                                requireSectorOptional.setText("");

                                Log.d(TAG, "onSuccess: Information stored successfully");
                                Toast.makeText(mContext, "Information stored successfully", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(mContext, "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });


                nagDialog.show();



            }
        });

        holder.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

                        String id = clientDataRequestModelArrayList.get(position).getCLIENT_ID();
                        Log.d(TAG, "onClick: id == > " + id);

                        DocumentReference documentReference = firebaseFirestore.collection("CLIENT REQUEST").document(id);

                        documentReference.delete().addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(mContext, "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                ClientRecyclerAdapter.this.notifyItemChanged(position);
                                clientDataRequestModelArrayList.remove(clientDataRequestModelArrayList.size()-1);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, clientDataRequestModelArrayList.size());

                                Toast.makeText(mContext, "Delete Successfully ", Toast.LENGTH_SHORT).show();

                            }
                        });



                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });


    }




    @Override
    public int getItemCount() {
        return clientDataRequestModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView Name, Number, clientRequirement, Rent, occupation, familyMember, requireSectorOptional, StatusOK, StatusPending;
        private Button button,button2;


        View mView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            button = itemView.findViewById(R.id.clientrequestedit);
            button2 = itemView.findViewById(R.id.clientrequestdelete);
        }


        public void Client_Name(String Client_NAME) {
            Name = mView.findViewById(R.id.clientname);
            Name.setText("Client Name: " + Client_NAME);
        }

        public void Client_PhoneNumber(String Client_number) {
            Number = mView.findViewById(R.id.clientPnone);
            Number.setText("Client PhoneNo: " + Client_number);
        }


        public void Client_Requirement(String requirement) {
            clientRequirement = mView.findViewById(R.id.clientRequirement);
            clientRequirement.setText(requirement);
        }

        public void Client_requested_rent(String requested_rent) {
            Rent = mView.findViewById(R.id.clientRequireRent);
            Rent.setText("Client Rent: " + requested_rent);
        }

        public void Client_Occupation(String occupationClient) {
            occupation = mView.findViewById(R.id.clientOccupation);
            occupation.setText("Client occupation: " + occupationClient);
        }

        public void ClientFamilyMember(String FamilyMember) {
            familyMember = mView.findViewById(R.id.clientFamilyMember);
            familyMember.setText("Client Family: " + FamilyMember);
        }

        public void Client_request_sector(String ClientSector) {
            requireSectorOptional = mView.findViewById(R.id.clientSectorRequire);
            requireSectorOptional.setText("Client request alaka " + ClientSector);
        }

        public void statuscomplete(boolean b) {
            StatusOK = mView.findViewById(R.id.statusOk);
            StatusPending = mView.findViewById(R.id.statuspending);
            if (b) {
                StatusOK.setVisibility(View.VISIBLE);
                StatusPending.setVisibility(View.GONE);
            }


        }

        public void statusPending(boolean b) {
            StatusOK = mView.findViewById(R.id.statusOk);
            StatusPending = mView.findViewById(R.id.statuspending);
            if (b) {
                StatusOK.setVisibility(View.GONE);
                StatusPending.setVisibility(View.VISIBLE);

            }

        }
    }
}
