package mx.rmr.menuhamburguesa.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import mx.rmr.menuhamburguesa.R

class MapasFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mapas, container, false)
        val webView = view.findViewById<WebView>(R.id.mapaweb)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.
        loadUrl("https://www.google.com/maps/d/viewer?mid=16p4XUJ3OiJqezpHleTAKGHy4Ti_8rYc&ll=19.575418665693352%2C-99.24592264703536&z=13")
        return view
    }
}